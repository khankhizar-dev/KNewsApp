package com.android.knewsapp.news.presentation.news_list

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.android.knewsapp.core_ui.theme.Dimensions
import com.android.knewsapp.news.R
import com.android.knewsapp.news.domain.model.Article
import com.android.knewsapp.news.presentation.news_list.components.ArticleSkeleton

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun NewsListScreen(
    viewModel: NewsListViewModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onArticleClick: (Article) -> Unit,
) {
    val articles by viewModel.articles.collectAsStateWithLifecycle()
    val bookmarkedArticles by viewModel.bookmarkedArticles.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val currentCountry by viewModel.country.collectAsStateWithLifecycle()
    val currentCategory by viewModel.category.collectAsStateWithLifecycle()
    val currentLanguage by viewModel.language.collectAsStateWithLifecycle()
    val currentSortBy by viewModel.sortBy.collectAsStateWithLifecycle()

    var showFilterSheet by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchExpanded by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }

    val countries = listOf("us", "gb", "in", "ca", "au", "ae", "sa", "fr", "de", "jp")
    val categories = listOf("business", "entertainment", "general", "health", "science", "sports", "technology")
    val languages = listOf("ar", "de", "en", "es", "fr", "he", "it", "nl", "no", "pt", "ru", "sv", "ud", "zh")
    val sortOptions =
        listOf(
            "publishedAt" to stringResource(R.string.latest),
            "title" to stringResource(R.string.title_a_z),
        )

    val displayArticles = if (selectedTab == 0) articles else bookmarkedArticles
    val skeletonCount = 5

    if (showFilterSheet) {
        ModalBottomSheet(onDismissRequest = { showFilterSheet = false }) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(Dimensions.PaddingLarge)
                        .verticalScroll(rememberScrollState()),
            ) {
                Text(
                    stringResource(R.string.filters_and_sorting),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))

                Text(stringResource(R.string.sort_by), style = MaterialTheme.typography.titleMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(sortOptions) { option ->
                        FilterChip(
                            selected = currentSortBy == option.first,
                            onClick = { viewModel.setSortBy(option.first) },
                            label = { Text(option.second) },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))

                Text(stringResource(R.string.country), style = MaterialTheme.typography.titleMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = currentCountry == null,
                            onClick = {
                                viewModel.setFilters(null, currentCategory, currentLanguage)
                            },
                            label = { Text(stringResource(R.string.global)) },
                        )
                    }
                    items(countries) { country ->
                        FilterChip(
                            selected = currentCountry == country,
                            onClick = {
                                viewModel.setFilters(country, currentCategory, currentLanguage)
                            },
                            label = { Text(country.uppercase()) },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))

                Text(stringResource(R.string.language_global_search), style = MaterialTheme.typography.titleMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = currentLanguage == null,
                            onClick = {
                                viewModel.setFilters(currentCountry, currentCategory, null)
                            },
                            label = { Text(stringResource(R.string.all)) },
                        )
                    }
                    items(languages) { language ->
                        FilterChip(
                            selected = currentLanguage == language,
                            onClick = {
                                viewModel.setFilters(null, null, language)
                            },
                            label = { Text(language.uppercase()) },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))

                Text(stringResource(R.string.category), style = MaterialTheme.typography.titleMedium)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    categories.forEach { category ->
                        FilterChip(
                            selected = currentCategory == category,
                            onClick = {
                                val newCategory = if (currentCategory == category) null else category
                                viewModel.setFilters(currentCountry, newCategory, currentLanguage)
                            },
                            label = { Text(category.replaceFirstChar { it.uppercase() }) },
                        )
                    }
                }
                Spacer(modifier = Modifier.height(Dimensions.SpacerLarge))
            }
        }
    }

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        if (isSearchExpanded) {
                            TextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text(stringResource(R.string.search_news)) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                colors =
                                    TextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                    ),
                            )
                        } else {
                            Text(
                                stringResource(R.string.app_name),
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    },
                    actions = {
                        if (isSearchExpanded) {
                            IconButton(
                                onClick = {
                                    if (searchQuery.isNotBlank()) {
                                        viewModel.searchNews(searchQuery)
                                    }
                                    isSearchExpanded = false
                                    searchQuery = ""
                                },
                            ) {
                                Icon(Icons.Default.Search, contentDescription = stringResource(R.string.confirm_search))
                            }
                            IconButton(
                                onClick = {
                                    isSearchExpanded = false
                                    searchQuery = ""
                                },
                            ) {
                                Icon(Icons.Default.Close, contentDescription = stringResource(R.string.close_search))
                            }
                        } else {
                            IconButton(onClick = { isSearchExpanded = true }) {
                                Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search))
                            }
                            IconButton(onClick = { viewModel.loadNews() }) {
                                Icon(Icons.Default.Refresh, contentDescription = stringResource(R.string.refresh))
                            }
                            IconButton(onClick = { showFilterSheet = true }) {
                                Icon(Icons.Default.List, contentDescription = stringResource(R.string.filter))
                            }
                        }
                    },
                    colors =
                        TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        ),
                )

                PrimaryTabRow(selectedTabIndex = selectedTab) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text(stringResource(R.string.discover)) },
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text(stringResource(R.string.bookmarks)) },
                    )
                }
            }
        },
    ) { innerPadding ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            if (loading && displayArticles.isEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Dimensions.PaddingMedium),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.PaddingMedium),
                ) {
                    items(skeletonCount) {
                        ArticleSkeleton()
                    }
                }
            } else if (error != null && displayArticles.isEmpty()) {
                Column(
                    modifier =
                        Modifier
                            .align(Alignment.Center)
                            .padding(Dimensions.PaddingLarge),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = error ?: stringResource(R.string.unknown_error),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))
                    Button(onClick = { viewModel.loadNews() }) {
                        Text(stringResource(R.string.retry))
                    }
                }
            } else if (!loading && displayArticles.isEmpty()) {
                val emptyText =
                    if (selectedTab == 0) {
                        stringResource(R.string.no_articles_found)
                    } else {
                        stringResource(R.string.no_bookmarks_saved)
                    }
                Column(
                    modifier =
                        Modifier
                            .align(Alignment.Center)
                            .padding(Dimensions.PaddingLarge),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = emptyText,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    if (selectedTab == 0) {
                        Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))
                        OutlinedButton(onClick = { viewModel.setFilters("us", null, null) }) {
                            Text(stringResource(R.string.reset_filters))
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Dimensions.PaddingMedium),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.PaddingMedium),
                ) {
                    items(displayArticles, key = { it.url }) { article ->
                        ArticleItem(
                            article = article,
                            sharedTransitionScope = sharedTransitionScope,
                            animatedContentScope = animatedContentScope,
                            onClick = { onArticleClick(article) },
                            onBookmarkClick = { viewModel.toggleBookmark(article) },
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ArticleItem(
    article: Article,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onClick: () -> Unit,
    onBookmarkClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            Box {
                article.urlToImage?.let { imageUrl ->
                    with(sharedTransitionScope) {
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = stringResource(R.string.article_image),
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .sharedElement(
                                        rememberSharedContentState(key = "image/${article.url}"),
                                        animatedVisibilityScope = animatedContentScope,
                                    ),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }

                IconButton(
                    onClick = onBookmarkClick,
                    modifier = Modifier.align(Alignment.TopEnd),
                ) {
                    val icon = if (article.isBookmarked) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                    Icon(
                        imageVector = icon,
                        contentDescription = stringResource(R.string.bookmark),
                        tint = if (article.isBookmarked) Color.Red else Color.White,
                    )
                }
            }
            Column(modifier = Modifier.padding(Dimensions.PaddingMedium)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                article.description?.let {
                    Spacer(modifier = Modifier.height(Dimensions.SpacerSmall))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(modifier = Modifier.height(Dimensions.SpacerSmall))
                Text(
                    text = article.source.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}
