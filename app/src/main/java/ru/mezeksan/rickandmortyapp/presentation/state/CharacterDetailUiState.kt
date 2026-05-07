package ru.mezeksan.rickandmortyapp.presentation.state

import ru.mezeksan.rickandmortyapp.domain.entity.CharacterDetail

sealed class CharacterDetailUiState {
    object Loading : CharacterDetailUiState()
    data class Success(val character: CharacterDetail) : CharacterDetailUiState()
    data class Error(val kind: UserErrorKind) : CharacterDetailUiState()
}
