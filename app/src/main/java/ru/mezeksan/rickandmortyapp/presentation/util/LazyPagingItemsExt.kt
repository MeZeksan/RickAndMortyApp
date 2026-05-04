package ru.mezeksan.rickandmortyapp.presentation.util

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

val <T : Any> LazyPagingItems<T>.isInitialRefreshLoading: Boolean
    get() = loadState.refresh is LoadState.Loading && itemCount == 0

val <T : Any> LazyPagingItems<T>.isInitialRefreshError: Boolean
    get() = loadState.refresh is LoadState.Error && itemCount == 0

val <T : Any> LazyPagingItems<T>.isEmptyAfterRefreshComplete: Boolean
    get() {
        val refresh = loadState.refresh
        val append = loadState.append
        return itemCount == 0 &&
                refresh is LoadState.NotLoading &&
                (refresh.endOfPaginationReached ||
                        (append is LoadState.NotLoading && append.endOfPaginationReached))
    }

val <T : Any> LazyPagingItems<T>.hasNoRenderedItems: Boolean
    get() = itemCount == 0

val <T : Any> LazyPagingItems<T>.appendShowsPaginationFooter: Boolean
    get() {
        val append = loadState.append
        return append is LoadState.Loading || append is LoadState.Error
    }
