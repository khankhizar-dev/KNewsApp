package com.android.knewsapp.news.presentation.news_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.android.knewsapp.core_ui.components.shimmerEffect
import com.android.knewsapp.core_ui.theme.Dimensions

private const val TITLE_WIDTH_FRACTION = 0.8f
private const val SUBTITLE_WIDTH_FRACTION = 0.5f
private const val DESCRIPTION_WIDTH_FRACTION_1 = 0.9f
private const val DESCRIPTION_WIDTH_FRACTION_2 = 0.7f

@Composable
fun ArticleSkeleton(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .shimmerEffect(),
            )
            Column(modifier = Modifier.padding(Dimensions.PaddingMedium)) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(TITLE_WIDTH_FRACTION)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerEffect(),
                )
                Spacer(modifier = Modifier.height(Dimensions.SpacerSmall))
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(SUBTITLE_WIDTH_FRACTION)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerEffect(),
                )
                Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(DESCRIPTION_WIDTH_FRACTION_1)
                            .height(14.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerEffect(),
                )
                Spacer(modifier = Modifier.height(Dimensions.SpacerSmall))
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(DESCRIPTION_WIDTH_FRACTION_2)
                            .height(14.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerEffect(),
                )
            }
        }
    }
}
