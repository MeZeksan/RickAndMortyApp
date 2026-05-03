package ru.mezeksan.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ru.mezeksan.rickandmortyapp.domain.entity.Character
import ru.mezeksan.rickandmortyapp.domain.usecase.GetCharactersUseCase
import ru.mezeksan.rickandmortyapp.presentation.intent.CharacterListIntent

class CharacterListViewModel(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    val charactersFlow: Flow<PagingData<Character>> = getCharactersUseCase()
        .cachedIn(viewModelScope)

    init {
        processIntent(CharacterListIntent.Load)
    }

    fun processIntent(intent: CharacterListIntent) {
        when (intent) {
            is CharacterListIntent.Load,
            is CharacterListIntent.Retry -> loadCharacters()
        }
    }

    private fun loadCharacters() = Unit
}
