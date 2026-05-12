package ru.mezeksan.rickandmortyapp.domain.model

data class CharacterListQuery(
    val name: String = "",
    val status: String? = null,
    val gender: String? = null,
    val species: String? = null
)
