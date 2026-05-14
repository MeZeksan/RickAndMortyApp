package ru.mezeksan.rickandmortyapp.presentation.model

import ru.mezeksan.rickandmortyapp.R

object CharacterFilterCatalog {
    val statusOptions: List<FilterOption> = listOf(
        FilterOption(R.string.filter_any, null),
        FilterOption(R.string.filter_status_alive, "alive"),
        FilterOption(R.string.filter_status_dead, "dead"),
        FilterOption(R.string.filter_status_unknown, "unknown")
    )

    val genderOptions: List<FilterOption> = listOf(
        FilterOption(R.string.filter_any, null),
        FilterOption(R.string.filter_gender_female, "female"),
        FilterOption(R.string.filter_gender_male, "male"),
        FilterOption(R.string.filter_gender_genderless, "genderless"),
        FilterOption(R.string.filter_gender_unknown, "unknown")
    )

    val speciesOptions: List<FilterOption> = listOf(
        FilterOption(R.string.filter_any, null),
        FilterOption(R.string.filter_species_human, "Human"),
        FilterOption(R.string.filter_species_alien, "Alien"),
        FilterOption(R.string.filter_species_robot, "Robot"),
        FilterOption(R.string.filter_species_vampire, "Vampire"),
        FilterOption(R.string.filter_species_demon, "Demon"),
        FilterOption(R.string.filter_species_mythological, "Mythological Creature")
    )

    fun optionFor(options: List<FilterOption>, apiValue: String?): FilterOption =
        options.firstOrNull { it.apiValue == apiValue } ?: options.first()
}
