package ru.mezeksan.rickandmortyapp.presentation.state

import ru.mezeksan.rickandmortyapp.domain.entity.Character

sealed interface CharacterListState {
    object Loading : CharacterListState
    data class Success(val characters: List<Character>) : CharacterListState
    data class Error(val message: String) : CharacterListState
}