package ru.mezeksan.rickandmortyapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mezeksan.rickandmortyapp.domain.usecase.GetCharactersUseCase

class CharacterListViewModelFactory(
    private val application: Application,
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterListViewModel(application, getCharactersUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
