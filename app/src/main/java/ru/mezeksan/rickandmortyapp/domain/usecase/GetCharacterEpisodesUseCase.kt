package ru.mezeksan.rickandmortyapp.domain.usecase

import ru.mezeksan.rickandmortyapp.domain.entity.Episode
import ru.mezeksan.rickandmortyapp.domain.repository.CharacterRepository

class GetCharacterEpisodesUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(ids: List<Int>): Result<List<Episode>> {
        return repository.getEpisodesByIds(ids)
    }
}
