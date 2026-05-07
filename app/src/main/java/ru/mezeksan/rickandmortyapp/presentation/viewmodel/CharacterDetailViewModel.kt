package ru.mezeksan.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.mezeksan.rickandmortyapp.domain.usecase.GetCharacterDetailUseCase
import ru.mezeksan.rickandmortyapp.presentation.state.CharacterDetailUiState
import ru.mezeksan.rickandmortyapp.presentation.util.toUserErrorKind

class CharacterDetailViewModel(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterDetailUiState>(CharacterDetailUiState.Loading)
    val uiState: StateFlow<CharacterDetailUiState> = _uiState.asStateFlow()

    fun loadCharacter(id: Int) {
        _uiState.value = CharacterDetailUiState.Loading
        viewModelScope.launch {
            getCharacterDetailUseCase(id)
                .onSuccess { character ->
                    _uiState.value = CharacterDetailUiState.Success(character)
                }
                .onFailure { error ->
                    _uiState.value = CharacterDetailUiState.Error(error.toUserErrorKind())
                }
        }
    }
}
