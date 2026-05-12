package ru.mezeksan.rickandmortyapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mezeksan.rickandmortyapp.data.dto.CharacterDto
import ru.mezeksan.rickandmortyapp.data.dto.CharacterListResponseDto

interface CharacterApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int? = null,
        @Query("name") name: String? = null
    ): CharacterListResponseDto

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): CharacterDto
}