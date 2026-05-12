package ru.mezeksan.rickandmortyapp.domain.usecase

import ru.mezeksan.rickandmortyapp.domain.entity.CharacterDetail
import ru.mezeksan.rickandmortyapp.domain.repository.CharacterRepository

class GetCharacterDetailUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): Result<CharacterDetail> {
        return repository.getCharacterById(id)
    }
}
