package ru.mezeksan.rickandmortyapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mezeksan.rickandmortyapp.data.dto.CharacterDto
import ru.mezeksan.rickandmortyapp.data.dto.CharacterListResponseDto
import ru.mezeksan.rickandmortyapp.data.dto.EpisodeDto

interface CharacterApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("gender") gender: String? = null,
        @Query("species") species: String? = null
    ): CharacterListResponseDto

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): CharacterDto

    @GET("episode/{id}")
    suspend fun getEpisodeById(
        @Path("id") id: Int
    ): EpisodeDto

    @GET("episode/{ids}")
    suspend fun getEpisodesByIds(
        @Path(value = "ids", encoded = true) ids: String
    ): List<EpisodeDto>
}