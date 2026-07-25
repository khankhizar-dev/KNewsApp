package com.android.knewsapp.news.presentation.news_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.android.knewsapp.core_ui.theme.Dimensions
import com.android.knewsapp.news.domain.model.Article

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun NewsListScreen(
    viewModel: NewsListViewModel,
    onArticleClick: (Article) -> Unit,
    onLogoutClick: () -> Unit
) {
    val articles by viewModel.articles.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val currentCountry by viewModel.country.collectAsStateWithLifecycle()
    val currentCategory by viewModel.category.collectAsStateWithLifecycle()
    val currentLanguage by viewModel.language.collectAsStateWithLifecycle()
    val currentSortBy by viewModel.sortBy.collectAsStateWithLifecycle()

    var showFilterSheet by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearchExpanded by remember { mutableStateOf(false) }

    val countries = listOf("us", "gb", "in", "ca", "au", "ae", "sa", "fr", "de", "jp")
    val categories = listOf("business", "entertainment", "general", "health", "science", "sports", "technology")
    val languages = listOf("ar", "de", "en", "es", "fr", "he", "it", "nl", "no", "pt", "ru", "sv", "ud", "zh")
    val sortOptions = listOf("publishedAt" to "Latest", "title" to "Title A-Z")

    if (showFilterSheet) {
        ModalBottomSheet(onDismissRequest = { showFilterSheet = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimensions.PaddingLarge)
                    .verticalScroll(rememberScrollState())
            ) {
                Text("Filters & Sorting", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))
                
                Text("Sort By", style = MaterialTheme.typography.titleMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(sortOptions) { option ->
                        FilterChip(
                            selected = currentSortBy == option.first,
                            onClick = { viewModel.setSortBy(option.first) },
                            label = { Text(option.second) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))

                Text("Country", style = MaterialTheme.typography.titleMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = currentCountry == null,
                            onClick = { viewModel.setFilters(null, currentCategory, currentLanguage) },
                            label = { Text("Global") }
                        )
                    }
                    items(countries) { country ->
                        FilterChip(
                            selected = currentCountry == country,
                            onClick = { viewModel.setFilters(country, currentCategory, currentLanguage) },
                            label = { Text(country.uppercase()) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))

                Text("Language (Global Search)", style = MaterialTheme.typography.titleMedium)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        FilterChip(
                            selected = currentLanguage == null,
                            onClick = { viewModel.setFilters(currentCountry, currentCategory, null) },
                            label = { Text("All") }
                        )
                    }
                    items(languages) { language ->
                        FilterChip(
                            selected = currentLanguage == language,
                            onClick = { viewModel.setFilters(null, null, language) }, // Clear country/category when picking language
                            label = { Text(language.uppercase()) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))

                Text("Category", style = MaterialTheme.typography.titleMedium)
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    categories.forEach { category ->
                        FilterChip(
                            selected = currentCategory == category,
                            onClick = { 
                                val newCategory = if (currentCategory == category) null else category
                                viewModel.setFilters(currentCountry, newCategory, currentLanguage)
                            },
                            label = { Text(category.replaceFirstChar { it.uppercase() }) }
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
                                placeholder = { Text("Search news...") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent
                                )
                            )
                        } else {
                            Text(
                                "KNews",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold
                            ) 
                        }
                    },
                    actions = {
                        if (isSearchExpanded) {
                            IconButton(onClick = { 
                                if (searchQuery.isNotBlank()) {
                                    viewModel.searchNews(searchQuery) 
                                }
                                isSearchExpanded = false 
                                searchQuery = ""
                            }) {
                                Icon(Icons.Default.Search, contentDescription = "Confirm Search")
                            }
                            IconButton(onClick = {
                                isSearchExpanded = false 
                                searchQuery = ""
                            }) {
                                Icon(Icons.Default.Close, contentDescription = "Close Search")
                            }
                        } else {
                            IconButton(onClick = { isSearchExpanded = true }) {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            }
                            IconButton(onClick = { viewModel.loadNews() }) {
                                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                            }
                            IconButton(onClick = { showFilterSheet = true }) {
                                Icon(Icons.Default.List, contentDescription = "Filter")
                            }
                            IconButton(onClick = onLogoutClick) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                    contentDescription = "Logout",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (loading && articles.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (error != null && articles.isEmpty()) {
                Column(
                    modifier = Modifier.align(Alignment.Center).padding(Dimensions.PaddingLarge),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = error ?: "Unknown error",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))
                    Button(onClick = { viewModel.loadNews() }) {
                        Text("Retry")
                    }
                }
            } else if (!loading && articles.isEmpty()) {
                Column(
                    modifier = Modifier.align(Alignment.Center).padding(Dimensions.PaddingLarge),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No articles found for this filter.",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(Dimensions.SpacerMedium))
                    OutlinedButton(onClick = { viewModel.setFilters("us", null, null) }) {
                        Text("Reset to US Headlines")
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(Dimensions.PaddingMedium),
                    verticalArrangement = Arrangement.spacedBy(Dimensions.PaddingMedium)
                ) {
                    items(articles) { article ->
                        ArticleItem(
                            article = article,
                            onClick = { onArticleClick(article) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleItem(
    article: Article,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            article.urlToImage?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Column(modifier = Modifier.padding(Dimensions.PaddingMedium)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                article.description?.let {
                    Spacer(modifier = Modifier.height(Dimensions.SpacerSmall))
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
