package ru.mezeksan.rickandmortyapp.domain.usecase

import ru.mezeksan.rickandmortyapp.domain.entity.Character
import ru.mezeksan.rickandmortyapp.domain.repository.CharacterRepository

class GetCharactersUseCase(private val repository: CharacterRepository) {
    suspend fun invoke(): Result<List<Character>>{
        return try{
            val characters = repository.getCharacters()
            Result.success(characters)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}