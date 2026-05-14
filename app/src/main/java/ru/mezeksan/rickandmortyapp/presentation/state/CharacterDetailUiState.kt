package ru.mezeksan.rickandmortyapp.presentation.state

import ru.mezeksan.rickandmortyapp.domain.entity.CharacterDetail
import ru.mezeksan.rickandmortyapp.domain.entity.Episode

sealed class CharacterDetailUiState {
    object Loading : CharacterDetailUiState()
    data class Success(
        val character: CharacterDetail,
        val episodes: EpisodesSectionUiState
    ) : CharacterDetailUiState()

    data class Error(val kind: UserErrorKind) : CharacterDetailUiState()
}

sealed class EpisodesSectionUiState {
    object Loading : EpisodesSectionUiState()
    data class Content(val items: List<Episode>) : EpisodesSectionUiState()
    data class Error(val kind: UserErrorKind) : EpisodesSectionUiState()
}
