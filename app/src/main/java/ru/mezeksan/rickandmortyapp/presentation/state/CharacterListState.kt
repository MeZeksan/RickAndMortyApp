package ru.mezeksan.rickandmortyapp.presentation.state

import kotlinx.collections.immutable.ImmutableList
import ru.mezeksan.rickandmortyapp.domain.entity.Character

sealed interface CharacterListState {
    object Loading : CharacterListState
    data class Success(val characters: ImmutableList<Character>) : CharacterListState
    data class Error(val message: String) : CharacterListState
}