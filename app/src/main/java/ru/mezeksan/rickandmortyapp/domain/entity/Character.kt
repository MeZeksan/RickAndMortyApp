package ru.mezeksan.rickandmortyapp.domain.entity

// поправить, убрать налбл заменить на приведение например к ""
data class Character(
    val id: Int?,
    val image: String?,
    val name: String?,
    val status: String?,
    val species: String?
)