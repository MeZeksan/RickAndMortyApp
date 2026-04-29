package ru.mezeksan.rickandmortyapp.data.mapper

import ru.mezeksan.rickandmortyapp.data.dto.CharacterDto
import ru.mezeksan.rickandmortyapp.domain.entity.Character

object CharacterMapper {
    fun mapFromDto(dto: CharacterDto): Character {
        return Character(
            id = dto.id ?: 0,
            image = dto.image.orEmpty(),
            name = dto.name ?: "Неизвестен",
            status = dto.status ?: "Неизвестен",
            species = dto.species ?: "Неизвестен",
        )
    }

    fun mapFromDtoList(dtoList: List<CharacterDto>?): List<Character> {
        return dtoList?.map { mapFromDto(it) } ?: emptyList()
    }
}