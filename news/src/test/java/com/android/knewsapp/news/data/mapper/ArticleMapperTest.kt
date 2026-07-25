package com.android.knewsapp.news.data.mapper

import com.android.knewsapp.news.domain.model.Article
import com.android.knewsapp.news.domain.model.Source
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ArticleMapperTest {
    @Test
    fun `toArticleEntity maps correctly`() {
        val article =
            Article(
                source = Source("id", "Name"),
                author = "Author",
                title = "Title",
                description = "Desc",
                url = "url",
                urlToImage = "image",
                publishedAt = "date",
                content = "content",
            )

        val entity = article.toArticleEntity("us", "business")

        assertThat(entity.url).isEqualTo(article.url)
        assertThat(entity.sourceName).isEqualTo(article.source.name)
        assertThat(entity.country).isEqualTo("us")
        assertThat(entity.category).isEqualTo("business")
    }

    @Test
    fun `ArticleEntity toArticle maps correctly`() {
        val entity =
            com.android.knewsapp.news.data.local.entity.ArticleEntity(
                url = "url",
                title = "Title",
                author = "Author",
                description = "Desc",
                urlToImage = "image",
                publishedAt = "date",
                content = "content",
                sourceName = "Source",
                country = "us",
                category = "business",
            )

        val domain = entity.toArticle()

        assertThat(domain.url).isEqualTo(entity.url)
        assertThat(domain.source.name).isEqualTo(entity.sourceName)
    }
}
