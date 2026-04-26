package ru.mezeksan.rickandmortyapp.presentation.intent

sealed interface CharacterListIntent {
    object Load : CharacterListIntent
    object Retry : CharacterListIntent
}