package com.android.knewsapp.news.presentation.news_list

import app.cash.turbine.test
import com.android.knewsapp.news.domain.model.Article
import com.android.knewsapp.news.domain.model.Source
import com.android.knewsapp.news.domain.repository.NewsRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsListViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var viewModel: NewsListViewModel
    private val repository: NewsRepository = mockk()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        val testArticles =
            listOf(
                Article(
                    source = Source(null, "Test"),
                    author = "Author",
                    title = "Title",
                    description = "Desc",
                    url = "url",
                    urlToImage = null,
                    publishedAt = "2024-01-01",
                    content = null,
                ),
            )

        every { repository.getArticles(any(), any(), any()) } returns flowOf(Result.success(testArticles))

        viewModel = NewsListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initialization loads articles from repository`() =
        runTest {
            viewModel.articles.test {
                val articles = awaitItem()
                assertThat(articles).isNotEmpty()
                assertThat(articles[0].title).isEqualTo("Title")
            }
        }

    @Test
    fun `loadNews updates error state on failure`() =
        runTest {
            val errorMessage = "Network Error"
            val failure = Result.failure<List<Article>>(Exception(errorMessage))
            every { repository.getArticles(any(), any(), any()) } returns flowOf(failure)

            viewModel.loadNews()

            viewModel.error.test {
                assertThat(awaitItem()).isEqualTo(errorMessage)
            }
        }

    @Test
    fun `setFilters updates state and reloads news`() =
        runTest {
            viewModel.setFilters("gb", "science", "en")

            assertThat(viewModel.country.value).isEqualTo("gb")
            assertThat(viewModel.category.value).isEqualTo("science")
            assertThat(viewModel.language.value).isEqualTo("en")

            verify { repository.getArticles("gb", "science", true) }
        }
}
