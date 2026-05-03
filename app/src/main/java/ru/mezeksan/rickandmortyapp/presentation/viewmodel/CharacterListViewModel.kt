package ru.mezeksan.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.mezeksan.rickandmortyapp.domain.usecase.GetCharactersUseCase
import ru.mezeksan.rickandmortyapp.presentation.intent.CharacterListIntent
import ru.mezeksan.rickandmortyapp.presentation.state.CharacterListState
import ru.mezeksan.rickandmortyapp.presentation.util.toUserErrorKind

class CharacterListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CharacterListState>(CharacterListState.Loading)
    val state: StateFlow<CharacterListState> = _state.asStateFlow()

    init {
        processIntent(CharacterListIntent.Load)
    }

    fun processIntent(intent: CharacterListIntent) {
        when (intent) {
            is CharacterListIntent.Load,
            is CharacterListIntent.Retry -> loadCharacters()
        }
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            _state.value = CharacterListState.Loading
            getCharactersUseCase.invoke()
                .onSuccess { characters ->
                    _state.value = CharacterListState.Success(characters.toImmutableList())
                }
                .onFailure { throwable ->
                    _state.value = CharacterListState.Error(throwable.toUserErrorKind())
                }
        }
    }
}
