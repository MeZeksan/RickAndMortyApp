package ru.mezeksan.rickandmortyapp.domain.entity

import androidx.compose.runtime.Stable

@Stable
data class Character(
    val id: Int,
    val image: String,
    val name: String,
    val status: String,
    val species: String
)
