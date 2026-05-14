package ru.mezeksan.rickandmortyapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.mezeksan.rickandmortyapp.domain.usecase.GetCharacterDetailUseCase
import ru.mezeksan.rickandmortyapp.domain.usecase.GetCharacterEpisodesUseCase
import ru.mezeksan.rickandmortyapp.presentation.intent.CharacterDetailIntent
import ru.mezeksan.rickandmortyapp.presentation.state.CharacterDetailUiState
import ru.mezeksan.rickandmortyapp.presentation.state.EpisodesSectionUiState
import ru.mezeksan.rickandmortyapp.presentation.util.toUserErrorKind

class CharacterDetailViewModel(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val getCharacterEpisodesUseCase: GetCharacterEpisodesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharacterDetailUiState>(CharacterDetailUiState.Loading)
    val uiState: StateFlow<CharacterDetailUiState> = _uiState.asStateFlow()

    fun dispatch(intent: CharacterDetailIntent) {
        when (intent) {
            is CharacterDetailIntent.LoadCharacter -> loadCharacter(intent.id)
            CharacterDetailIntent.EpisodesRetryClicked -> retryEpisodes()
        }
    }

    private fun loadCharacter(id: Int) {
        _uiState.value = CharacterDetailUiState.Loading
        viewModelScope.launch {
            getCharacterDetailUseCase(id)
                .onSuccess { character ->
                    val episodesInitial = if (character.episodeIds.isEmpty()) {
                        EpisodesSectionUiState.Content(emptyList())
                    } else {
                        EpisodesSectionUiState.Loading
                    }
                    _uiState.value = CharacterDetailUiState.Success(
                        character = character,
                        episodes = episodesInitial
                    )
                    if (character.episodeIds.isNotEmpty()) {
                        fetchEpisodes(characterId = character.id, episodeIds = character.episodeIds)
                    }
                }
                .onFailure { error ->
                    _uiState.value = CharacterDetailUiState.Error(error.toUserErrorKind())
                }
        }
    }

    private fun retryEpisodes() {
        val state = _uiState.value as? CharacterDetailUiState.Success ?: return
        if (state.character.episodeIds.isEmpty()) return
        viewModelScope.launch {
            _uiState.value = state.copy(episodes = EpisodesSectionUiState.Loading)
            fetchEpisodes(
                characterId = state.character.id,
                episodeIds = state.character.episodeIds
            )
        }
    }

    private suspend fun fetchEpisodes(characterId: Int, episodeIds: List<Int>) {
        getCharacterEpisodesUseCase(episodeIds)
            .onSuccess { episodes ->
                applyEpisodesResult(characterId, EpisodesSectionUiState.Content(episodes))
            }
            .onFailure { error ->
                applyEpisodesResult(
                    characterId,
                    EpisodesSectionUiState.Error(error.toUserErrorKind())
                )
            }
    }

    private fun applyEpisodesResult(
        characterId: Int,
        episodes: EpisodesSectionUiState
    ) {
        val current = _uiState.value
        if (current is CharacterDetailUiState.Success && current.character.id == characterId) {
            _uiState.value = current.copy(episodes = episodes)
        }
    }
}
