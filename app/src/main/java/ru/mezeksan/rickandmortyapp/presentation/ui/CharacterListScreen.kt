package ru.mezeksan.rickandmortyapp.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import org.koin.androidx.compose.koinViewModel
import ru.mezeksan.rickandmortyapp.R
import ru.mezeksan.rickandmortyapp.presentation.state.UserErrorKind
import ru.mezeksan.rickandmortyapp.presentation.ui.components.CharacterCard
import ru.mezeksan.rickandmortyapp.presentation.ui.components.EmptyContent
import ru.mezeksan.rickandmortyapp.presentation.ui.components.ErrorContent
import ru.mezeksan.rickandmortyapp.presentation.ui.components.LoadingContent
import ru.mezeksan.rickandmortyapp.presentation.ui.components.SearchBar
import ru.mezeksan.rickandmortyapp.presentation.util.appendShowsPaginationFooter
import ru.mezeksan.rickandmortyapp.presentation.util.hasNoRenderedItems
import ru.mezeksan.rickandmortyapp.presentation.util.isEmptyAfterRefreshComplete
import ru.mezeksan.rickandmortyapp.presentation.util.isInitialRefreshError
import ru.mezeksan.rickandmortyapp.presentation.util.isInitialRefreshLoading
import ru.mezeksan.rickandmortyapp.presentation.util.toUserErrorKind
import ru.mezeksan.rickandmortyapp.presentation.viewmodel.CharacterListViewModel
import ru.mezeksan.rickandmortyapp.ui.theme.PortalGreen
import ru.mezeksan.rickandmortyapp.ui.theme.ToxicText

@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel = koinViewModel()
) {
    val lazyPagingItems = viewModel.charactersFlow.collectAsLazyPagingItems()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF090E1A),
                        Color(0xFF111A2F),
                        Color(0xFF172549)
                    )
                )
            )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenHeader(
                searchQuery = searchQuery,
                onSearchQueryChanged = viewModel::onSearchQueryChanged
            )

            when {
                lazyPagingItems.isInitialRefreshLoading -> {
                    LoadingContent()
                }

                lazyPagingItems.isInitialRefreshError -> {
                    val error = (lazyPagingItems.loadState.refresh as LoadState.Error).error
                    ErrorContent(
                        kind = error.toUserErrorKind(),
                        onRetry = { lazyPagingItems.retry() }
                    )
                }

                else -> {
                    when {
                        lazyPagingItems.isEmptyAfterRefreshComplete -> {
                            EmptyContent(searchQuery = searchQuery)
                        }

                        lazyPagingItems.hasNoRenderedItems -> {
                            LoadingContent()
                        }

                        else -> {
                            LazyColumn(
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(
                                    count = lazyPagingItems.itemCount,
                                    key = lazyPagingItems.itemKey { it.id }
                                ) { index ->
                                    val character = lazyPagingItems[index]
                                    character?.let {
                                        val unknown =
                                            stringResource(R.string.unknown_character_field)
                                        CharacterCard(
                                            character = it,
                                            photoContentDescription = stringResource(
                                                R.string.character_photo_content_description,
                                                it.name.ifBlank { unknown }
                                            )
                                        )
                                    }
                                }

                                val appendState = lazyPagingItems.loadState.append
                                items(
                                    count = if (lazyPagingItems.appendShowsPaginationFooter) 1 else 0,
                                    key = { "pagination_footer" }
                                ) {
                                    when (appendState) {
                                        is LoadState.Loading -> PaginationLoadingIndicator()
                                        is LoadState.Error -> PaginationErrorItem(
                                            kind = appendState.error.toUserErrorKind(),
                                            onRetry = { lazyPagingItems.retry() }
                                        )

                                        else -> {}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ScreenHeader(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 8.dp)
    ) {
        Text(
            text = stringResource(R.string.character_list_title),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = PortalGreen
        )
        Spacer(modifier = Modifier.height(12.dp))
        SearchBar(
            query = searchQuery,
            onQueryChanged = onSearchQueryChanged
        )
    }
}

@Composable
private fun PaginationLoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = PortalGreen)
    }
}

@Composable
private fun PaginationErrorItem(
    kind: UserErrorKind,
    onRetry: () -> Unit
) {
    val messageRes = when (kind) {
        UserErrorKind.Network -> R.string.error_message_network
        UserErrorKind.Server -> R.string.error_message_server
        UserErrorKind.Generic -> R.string.error_message_generic
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(messageRes),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = ToxicText
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = PortalGreen,
                contentColor = Color(0xFF0C1322)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(stringResource(R.string.retry))
        }
    }
}
