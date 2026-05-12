package ru.mezeksan.rickandmortyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.mezeksan.rickandmortyapp.presentation.ui.CharacterDetailScreen
import ru.mezeksan.rickandmortyapp.presentation.ui.CharacterListScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoutes.CharacterList.route) {
        composable(AppRoutes.CharacterList.route) {
            CharacterListScreen(
                onCharacterClick = { characterId ->
                    navController.navigate(AppRoutes.CharacterDetail.createRoute(characterId))
                }
            )
        }
        
        composable(
            route = AppRoutes.CharacterDetail.route,
            arguments = listOf(navArgument("characterId") { type = NavType.IntType })
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getInt("characterId") ?: return@composable
            CharacterDetailScreen(
                characterId = characterId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
