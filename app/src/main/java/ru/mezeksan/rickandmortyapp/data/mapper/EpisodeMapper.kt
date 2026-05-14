package ru.mezeksan.rickandmortyapp.data.mapper

import ru.mezeksan.rickandmortyapp.data.dto.EpisodeDto
import ru.mezeksan.rickandmortyapp.domain.entity.Episode

object EpisodeMapper {
    fun mapFromDto(dto: EpisodeDto): Episode {
        return Episode(
            id = dto.id ?: 0,
            name = dto.name.orEmpty(),
            code = dto.episode.orEmpty(),
            airDate = dto.airDate.orEmpty()
        )
    }
}
