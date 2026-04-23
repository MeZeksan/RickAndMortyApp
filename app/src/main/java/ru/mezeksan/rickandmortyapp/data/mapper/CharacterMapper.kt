package ru.mezeksan.rickandmortyapp.data.mapper

import ru.mezeksan.rickandmortyapp.data.dto.CharacterDto
import ru.mezeksan.rickandmortyapp.domain.Character

object CharacterMapper {
    fun mapFromDto(dto: CharacterDto): Character{
        return Character(
            id = dto.id,
            image = dto.image,
            name = dto.name,
            status = dto.status,
            species = dto.species
        )
    }

    fun mapFromDtoList(dtoList: List<CharacterDto>?): List<Character>{
        return dtoList?.map{mapFromDto(it)}?: emptyList()
    }
}