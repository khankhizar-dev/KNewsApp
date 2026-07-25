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

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle

    private val _fullContent = MutableStateFlow<String?>(null)
    val fullContent: StateFlow<String?> = _fullContent

    // Filters
    private val _country = MutableStateFlow<String?>("us")
    val country: StateFlow<String?> = _country

    private val _category = MutableStateFlow<String?>(null)
    val category: StateFlow<String?> = _category

    private val _language = MutableStateFlow<String?>(null)
    val language: StateFlow<String?> = _language

    private val _sortBy = MutableStateFlow("publishedAt")
    val sortBy: StateFlow<String> = _sortBy

    init {
        loadNews()
    }

    fun setFilters(country: String?, category: String?, language: String?) {
        _country.value = country
        _category.value = category
        _language.value = language
        loadNews()
    }

    fun searchNews(query: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            repository.getEverything(query = query, language = _language.value)
                .onSuccess { articles ->
                    _articles.value = articles
                    sortArticles()
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Search failed"
                    _articles.value = emptyList()
                }
            _loading.value = false
        }
    }

    fun setSortBy(sortBy: String) {
        _sortBy.value = sortBy
        sortArticles()
    }

    private fun sortArticles() {
        val currentList = _articles.value.toMutableList()
        when (_sortBy.value) {
            "publishedAt" -> currentList.sortByDescending { it.publishedAt }
            "title" -> currentList.sortBy { it.title }
        }
        _articles.value = currentList
    }

    fun selectArticle(article: Article?) {
        _selectedArticle.value = article
        _fullContent.value = null // Reset
        if (article != null) {
            fetchFullContent(article.url)
        }
    }

    private fun fetchFullContent(url: String) {
        viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            try {
                val doc = org.jsoup.Jsoup.connect(url).get()
                val paragraphs = doc.select("p").map { it.text() }
                val content = paragraphs.joinToString("\n\n")
                _fullContent.value = content
            } catch (e: Exception) {
                _fullContent.value = "Failed to load full content. Error: ${e.message}"
            }
        }
    }

    fun loadNews(fetchFromRemote: Boolean = true) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            
            repository.getArticles(
                country = _country.value,
                category = _category.value,
                fetchFromRemote = fetchFromRemote
            ).collect { result ->
                result
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
}
