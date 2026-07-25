package com.android.knewsapp.news.presentation.news_detail

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.android.knewsapp.core_ui.theme.Dimensions
import com.android.knewsapp.news.R
import com.android.knewsapp.news.domain.model.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(
    article: Article,
    fullStory: String?,
    onBackClick: () -> Unit,
    onBookmarkClick: () -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(article.source.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            val sendIntent: Intent =
                                Intent().apply {
                                    action = Intent.ACTION_SEND
                                    val text = "${article.title}\n\nRead more at: ${article.url}"
                                    putExtra(Intent.EXTRA_TEXT, text)
                                    type = "text/plain"
                                }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            context.startActivity(shareIntent)
                        },
                    ) {
                        Icon(Icons.Default.Share, contentDescription = stringResource(R.string.share))
                    }
                    IconButton(onClick = onBookmarkClick) {
                        Icon(
                            imageVector =
                                if (article.isBookmarked) {
                                    Icons.Default.Favorite
                                } else {
                                    Icons.Default.FavoriteBorder
                                },
                            contentDescription = stringResource(R.string.bookmark),
                            tint = if (article.isBookmarked) Color.Red else LocalContentColor.current,
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
        ) {
            article.urlToImage?.let {
                AsyncImage(
                    model = it,
                    contentDescription = stringResource(R.string.article_image),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                    contentScale = ContentScale.Crop,
                )
            }

            Column(modifier = Modifier.padding(Dimensions.PaddingMedium)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(Dimensions.SpacerSmall))

                val authorText = "By ${article.author ?: "Unknown Source"} • ${article.publishedAt.take(10)}"
                Text(
                    text = authorText,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )

                Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))

                if (fullStory == null) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                    ) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                } else {
                    Text(
                        text = fullStory,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }

                Spacer(modifier = Modifier.height(Dimensions.SpacerLarge))
            }
        }
    }
}
