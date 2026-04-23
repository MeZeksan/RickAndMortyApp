package ru.mezeksan.rickandmortyapp.domain.repository

import ru.mezeksan.rickandmortyapp.domain.entity.Character

interface CharacterRepository {
    suspend fun getCharacters(): List<Character>

}