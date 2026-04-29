package ru.mezeksan.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ru.mezeksan.rickandmortyapp.data.remote.ApiClient
import ru.mezeksan.rickandmortyapp.data.remote.CharacterApi
import ru.mezeksan.rickandmortyapp.data.repository.CharacterRepositoryImpl
import ru.mezeksan.rickandmortyapp.domain.usecase.GetCharactersUseCase
import ru.mezeksan.rickandmortyapp.presentation.ui.CharacterListScreen
import ru.mezeksan.rickandmortyapp.presentation.viewmodel.CharacterListViewModelFactory
import ru.mezeksan.rickandmortyapp.ui.theme.RickAndMortyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RickAndMortyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CharacterListScreen()
                }
            }
        }
    }
}