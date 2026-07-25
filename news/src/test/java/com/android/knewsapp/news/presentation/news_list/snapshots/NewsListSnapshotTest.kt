package com.android.knewsapp.news.presentation.news_list.snapshots

import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.knewsapp.core_ui.theme.KNewsAppTheme
import com.android.knewsapp.news.domain.model.Article
import com.android.knewsapp.news.domain.model.Source
import com.android.knewsapp.news.presentation.news_list.ArticleItem
import org.junit.Rule
import org.junit.Test

class NewsListSnapshotTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = DeviceConfig.PIXEL_5
    )

    @Test
    fun articleItem_snapshot() {
        val article = Article(
            source = Source(null, "BBC News"),
            author = "John Doe",
            title = "Testing Snapshot for News Item",
            description = "A short description for the snapshot test.",
            url = "url",
            urlToImage = null,
            publishedAt = "2024-01-01",
            content = null
        )

        paparazzi.snapshot {
            KNewsAppTheme {
                ArticleItem(
                    article = article,
                    onClick = {}
                )
            }
        }
    }
}
