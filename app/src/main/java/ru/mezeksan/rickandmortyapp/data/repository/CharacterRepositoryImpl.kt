package ru.mezeksan.rickandmortyapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.mezeksan.rickandmortyapp.data.mapper.CharacterMapper
import ru.mezeksan.rickandmortyapp.data.mapper.EpisodeMapper
import ru.mezeksan.rickandmortyapp.data.paging.CharacterPagingSource
import ru.mezeksan.rickandmortyapp.data.remote.CharacterApi
import ru.mezeksan.rickandmortyapp.domain.entity.Character
import ru.mezeksan.rickandmortyapp.domain.model.CharacterListQuery
import ru.mezeksan.rickandmortyapp.domain.entity.CharacterDetail
import ru.mezeksan.rickandmortyapp.domain.entity.Episode
import ru.mezeksan.rickandmortyapp.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val api: CharacterApi
) : CharacterRepository {

    companion object {
        private const val PAGE_SIZE = 20
    }

    override fun getCharacters(query: CharacterListQuery): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharacterPagingSource(api, query) }
        ).flow
    }

    override suspend fun getCharacterById(id: Int): Result<CharacterDetail> {
        return try {
            val dto = api.getCharacterById(id)
            Result.success(CharacterMapper.mapToDetail(dto))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getEpisodesByIds(ids: List<Int>): Result<List<Episode>> {
        if (ids.isEmpty()) return Result.success(emptyList())
        return try {
            val dtos = when (ids.size) {
                1 -> listOf(api.getEpisodeById(ids.first()))
                else -> api.getEpisodesByIds(ids.joinToString(","))
            }
            val byId = dtos.map { EpisodeMapper.mapFromDto(it) }.associateBy { it.id }
            val ordered = ids.mapNotNull { byId[it] }
            Result.success(ordered)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}