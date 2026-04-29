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
// result лучше вынести в репозиторий, в нем обрабатывать ошибки и возвращать результат. то есть сетевую ошибку в дата слое
// use case обрабатывает ошибки логические с точки зрения приложения, а не дата слоя