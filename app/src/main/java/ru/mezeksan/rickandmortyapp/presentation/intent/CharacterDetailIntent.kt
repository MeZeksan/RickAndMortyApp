package ru.mezeksan.rickandmortyapp.presentation.intent

sealed interface CharacterDetailIntent {
    data class LoadCharacter(val id: Int) : CharacterDetailIntent
    data object EpisodesRetryClicked : CharacterDetailIntent
}
