package ru.mezeksan.rickandmortyapp.navigation

sealed class AppRoutes(val route: String) {
    object CharacterList : AppRoutes("character_list")
    object CharacterDetail : AppRoutes("character_detail/{characterId}") {
        fun createRoute(characterId: Int) = "character_detail/$characterId"
    }
}
