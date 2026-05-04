package ru.mezeksan.rickandmortyapp.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.mezeksan.rickandmortyapp.domain.entity.Character
import ru.mezeksan.rickandmortyapp.domain.repository.CharacterRepository

class GetCharactersUseCase(private val repository: CharacterRepository) {
    operator fun invoke(): Flow<PagingData<Character>> {
        return repository.getCharacters()
    }
}
