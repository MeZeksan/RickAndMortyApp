package ru.mezeksan.rickandmortyapp.presentation.intent

sealed interface CharacterListIntent {
    data class SearchQueryChanged(val query: String) : CharacterListIntent
    data class StatusFilterChanged(val apiValue: String?) : CharacterListIntent
    data class GenderFilterChanged(val apiValue: String?) : CharacterListIntent
    data class SpeciesFilterChanged(val apiValue: String?) : CharacterListIntent
}
