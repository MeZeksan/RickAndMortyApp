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
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flatMapLatest
import ru.mezeksan.rickandmortyapp.domain.entity.Character
import ru.mezeksan.rickandmortyapp.domain.model.CharacterListQuery
import ru.mezeksan.rickandmortyapp.domain.usecase.GetCharactersUseCase
import ru.mezeksan.rickandmortyapp.presentation.model.CharacterListFilters

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class CharacterListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filters = MutableStateFlow(CharacterListFilters())
    val filters: StateFlow<CharacterListFilters> = _filters.asStateFlow()

    private val searchForPaging: Flow<String> = channelFlow {
        send(_searchQuery.value)
        _searchQuery
            .drop(1)
            .debounce(400L)
            .distinctUntilChanged()
            .collect { send(it) }
    }.distinctUntilChanged()

    val charactersFlow: Flow<PagingData<Character>> = combine(
        searchForPaging,
        _filters
    ) { name, filters ->
        CharacterListQuery(
            name = name,
            status = filters.status,
            gender = filters.gender,
            species = filters.species
        )
    }
        .distinctUntilChanged()
        .flatMapLatest { query -> getCharactersUseCase(query) }
        .cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onStatusFilterChanged(apiValue: String?) {
        _filters.value = _filters.value.copy(status = apiValue)
    }

    fun onGenderFilterChanged(apiValue: String?) {
        _filters.value = _filters.value.copy(gender = apiValue)
    }

    fun onSpeciesFilterChanged(apiValue: String?) {
        _filters.value = _filters.value.copy(species = apiValue)
    }
}
