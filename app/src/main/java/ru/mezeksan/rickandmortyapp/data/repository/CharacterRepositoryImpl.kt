package ru.mezeksan.rickandmortyapp.data.repository

import ru.mezeksan.rickandmortyapp.data.mapper.CharacterMapper
import ru.mezeksan.rickandmortyapp.data.remote.CharacterApi
import ru.mezeksan.rickandmortyapp.domain.entity.Character
import ru.mezeksan.rickandmortyapp.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val api: CharacterApi
): CharacterRepository {
    override suspend fun getCharacters(): List<Character> {
        val response = api.getCharacters()
        return CharacterMapper.mapFromDtoList(response.results)
    }
}