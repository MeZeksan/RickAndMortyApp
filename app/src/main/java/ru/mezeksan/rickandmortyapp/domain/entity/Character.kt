package ru.mezeksan.rickandmortyapp.domain.entity

data class Character(
    val id: Int,
    val image: String,
    val name: String,
    val status: String,
    val species: String
)
