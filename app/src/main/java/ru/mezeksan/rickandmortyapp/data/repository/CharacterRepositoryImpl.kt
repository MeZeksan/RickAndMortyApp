package ru.mezeksan.rickandmortyapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.mezeksan.rickandmortyapp.data.paging.CharacterPagingSource
import ru.mezeksan.rickandmortyapp.data.remote.CharacterApi
import ru.mezeksan.rickandmortyapp.domain.entity.Character
import ru.mezeksan.rickandmortyapp.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val api: CharacterApi
) : CharacterRepository {

    companion object {
        private const val PAGE_SIZE = 20
    }

    override fun getCharacters(query: String): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharacterPagingSource(api, query) }
        ).flow
    }
}