package com.android.knewsapp.news.data.mapper

import com.android.knewsapp.news.data.local.entity.ArticleEntity
import com.android.knewsapp.news.data.local.entity.BookmarkEntity
import com.android.knewsapp.news.domain.model.Article
import com.android.knewsapp.news.domain.model.Source

fun Article.toArticleEntity(
    country: String?,
    category: String?,
): ArticleEntity {
    return ArticleEntity(
        url = url,
        title = title,
        author = author,
        description = description,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
        sourceName = source.name,
        country = country,
        category = category,
    )
}

fun ArticleEntity.toArticle(isBookmarked: Boolean = false): Article {
    return Article(
        source = Source(id = null, name = sourceName),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
        isBookmarked = isBookmarked,
    )
}

fun Article.toBookmarkEntity(): BookmarkEntity {
    return BookmarkEntity(
        url = url,
        title = title,
        author = author,
        description = description,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
        sourceName = source.name,
    )
}

fun BookmarkEntity.toArticle(): Article {
    return Article(
        source = Source(id = null, name = sourceName),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
        isBookmarked = true,
    )
}
