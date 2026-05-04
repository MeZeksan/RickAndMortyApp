package ru.mezeksan.rickandmortyapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.mezeksan.rickandmortyapp.data.mapper.CharacterMapper
import ru.mezeksan.rickandmortyapp.data.remote.CharacterApi
import ru.mezeksan.rickandmortyapp.domain.entity.Character

class CharacterPagingSource(
    private val api: CharacterApi
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val currentPage = params.key ?: 1
            val response = api.getCharacters(page = currentPage)

            val characters = CharacterMapper.mapFromDtoList(response.results)

            val nextPage = if (response.info?.next != null) currentPage + 1 else null

            LoadResult.Page(
                data = characters,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
