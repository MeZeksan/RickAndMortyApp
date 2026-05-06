package ru.mezeksan.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import ru.mezeksan.rickandmortyapp.domain.entity.Character
import ru.mezeksan.rickandmortyapp.domain.usecase.GetCharactersUseCase

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class CharacterListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    val charactersFlow: Flow<PagingData<Character>> = _searchQuery
        .debounce(400L)
        .distinctUntilChanged()
        .flatMapLatest { query -> getCharactersUseCase(query) }
        .cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}
