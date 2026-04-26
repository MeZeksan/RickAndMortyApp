package ru.mezeksan.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mezeksan.rickandmortyapp.domain.usecase.GetCharactersUseCase

class CharacterListViewModelFactory(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterListViewModel(getCharactersUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
