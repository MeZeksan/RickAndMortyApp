package ru.mezeksan.rickandmortyapp.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.mezeksan.rickandmortyapp.domain.entity.Character
import ru.mezeksan.rickandmortyapp.domain.entity.CharacterDetail
import ru.mezeksan.rickandmortyapp.domain.model.CharacterListQuery

interface CharacterRepository {
    fun getCharacters(query: CharacterListQuery = CharacterListQuery()): Flow<PagingData<Character>>
    suspend fun getCharacterById(id: Int): Result<CharacterDetail>
}