package com.android.knewsapp.news.presentation.news_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.knewsapp.news.domain.model.Article
import com.android.knewsapp.news.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadNews()
    }

    fun loadNews() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            repository.getTopHeadlines()
                .onSuccess { articles ->
                    _articles.value = articles
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Failed to load news"
                }
            _loading.value = false
        }
    }
}
