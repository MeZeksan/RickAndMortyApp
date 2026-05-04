package ru.mezeksan.rickandmortyapp.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.mezeksan.rickandmortyapp.domain.entity.Character

interface CharacterRepository {
    fun getCharacters(): Flow<PagingData<Character>>
}