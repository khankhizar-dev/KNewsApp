package com.android.knewsapp.news.presentation.news_list

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.android.knewsapp.news.domain.model.Article
import com.android.knewsapp.news.domain.model.Source
import com.android.knewsapp.news.domain.repository.NewsRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class NewsListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val repository: NewsRepository = mockk()

    @Test
    fun newsListScreen_displaysTitle() {
        val testArticles =
            listOf(
                Article(
                    source = Source(null, "Test"),
                    author = "Author",
                    title = "Amazing News Title",
                    description = "Desc",
                    url = "url",
                    urlToImage = null,
                    publishedAt = "2024-01-01",
                    content = null,
                ),
            )

        every { repository.getArticles(any(), any(), any()) } returns flowOf(Result.success(testArticles))
        val viewModel = NewsListViewModel(repository)

        composeTestRule.setContent {
            NewsListScreen(
                viewModel = viewModel,
                onArticleClick = {},
                onLogoutClick = {},
            )
        }

        composeTestRule.onNodeWithText("Amazing News Title").assertIsDisplayed()
    }
}
