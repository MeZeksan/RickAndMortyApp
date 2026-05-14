package ru.mezeksan.rickandmortyapp.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import ru.mezeksan.rickandmortyapp.R
import ru.mezeksan.rickandmortyapp.domain.entity.CharacterDetail
import ru.mezeksan.rickandmortyapp.domain.entity.Episode
import ru.mezeksan.rickandmortyapp.presentation.intent.CharacterDetailIntent
import ru.mezeksan.rickandmortyapp.presentation.state.CharacterDetailUiState
import ru.mezeksan.rickandmortyapp.presentation.state.EpisodesSectionUiState
import ru.mezeksan.rickandmortyapp.presentation.state.UserErrorKind
import ru.mezeksan.rickandmortyapp.presentation.ui.components.ErrorContent
import ru.mezeksan.rickandmortyapp.presentation.ui.components.LoadingContent
import ru.mezeksan.rickandmortyapp.presentation.viewmodel.CharacterDetailViewModel
import ru.mezeksan.rickandmortyapp.ui.theme.PortalBlue
import ru.mezeksan.rickandmortyapp.ui.theme.PortalGreen
import ru.mezeksan.rickandmortyapp.ui.theme.Red
import ru.mezeksan.rickandmortyapp.ui.theme.SpaceCard
import ru.mezeksan.rickandmortyapp.ui.theme.ToxicText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    characterId: Int,
    onBackClick: () -> Unit,
    viewModel: CharacterDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(characterId) {
        viewModel.dispatch(CharacterDetailIntent.LoadCharacter(characterId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.character_detail_title), color = PortalGreen) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = PortalGreen
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF090E1A)
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF090E1A),
                            Color(0xFF111A2F),
                            Color(0xFF172549)
                        )
                    )
                )
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is CharacterDetailUiState.Loading -> {
                    LoadingContent()
                }
                is CharacterDetailUiState.Error -> {
                    ErrorContent(
                        kind = state.kind,
                        onRetry = { viewModel.dispatch(CharacterDetailIntent.LoadCharacter(characterId)) }
                    )
                }
                is CharacterDetailUiState.Success -> {
                    CharacterDetailContent(
                        character = state.character,
                        episodes = state.episodes,
                        onEpisodesRetry = {
                            viewModel.dispatch(CharacterDetailIntent.EpisodesRetryClicked)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterDetailContent(
    character: CharacterDetail,
    episodes: EpisodesSectionUiState,
    onEpisodesRetry: () -> Unit
) {
    val statusColor = when (character.status.lowercase()) {
        "alive" -> PortalGreen
        "dead" -> Red
        else -> PortalBlue
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = character.name,
                    placeholder = painterResource(id = R.drawable.ic_character_placeholder),
                    error = painterResource(id = R.drawable.ic_character_placeholder),
                    fallback = painterResource(id = R.drawable.ic_character_placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(24.dp))
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = character.name.ifBlank { stringResource(R.string.unknown_character_field) },
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = ToxicText
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(statusColor)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${character.status} - ${character.species}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                InfoCard(title = "Gender", value = character.gender)
                Spacer(modifier = Modifier.height(12.dp))
                InfoCard(title = "Origin", value = character.origin)
                Spacer(modifier = Modifier.height(12.dp))
                InfoCard(title = "Last known location", value = character.location)
            }
        }
        item {
            EpisodesSection(
                state = episodes,
                onRetry = onEpisodesRetry,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp)
            )
        }
    }
}

@Composable
private fun EpisodesSection(
    state: EpisodesSectionUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.episodes_section_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = ToxicText
        )
        Spacer(modifier = Modifier.height(12.dp))
        when (state) {
            EpisodesSectionUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = PortalGreen)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = stringResource(R.string.episodes_loading),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }
            is EpisodesSectionUiState.Content -> {
                if (state.items.isEmpty()) {
                    Text(
                        text = stringResource(R.string.episodes_empty),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White.copy(alpha = 0.85f),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        state.items.forEach { episode ->
                            EpisodeCard(episode = episode)
                        }
                    }
                }
            }
            is EpisodesSectionUiState.Error -> {
                EpisodesSectionError(kind = state.kind, onRetry = onRetry)
            }
        }
    }
}

@Composable
private fun EpisodesSectionError(
    kind: UserErrorKind,
    onRetry: () -> Unit
) {
    val messageRes = when (kind) {
        UserErrorKind.Network -> R.string.error_message_network
        UserErrorKind.Server -> R.string.error_message_server
        UserErrorKind.Generic -> R.string.error_message_generic
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SpaceCard, RoundedCornerShape(16.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.episodes_error_title),
            style = MaterialTheme.typography.titleMedium,
            color = PortalGreen,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(messageRes),
            style = MaterialTheme.typography.bodyMedium,
            color = ToxicText,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = PortalGreen,
                contentColor = Color(0xFF0C1322)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
private fun EpisodeCard(episode: Episode) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SpaceCard, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = episode.name.ifBlank { stringResource(R.string.unknown_character_field) },
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = ToxicText
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = episode.code.ifBlank { stringResource(R.string.unknown_character_field) },
            style = MaterialTheme.typography.bodyMedium,
            color = PortalBlue
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = episode.airDate.ifBlank { stringResource(R.string.unknown_character_field) },
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.85f)
        )
    }
}

@Composable
fun InfoCard(title: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(SpaceCard, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = PortalBlue
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value.ifBlank { stringResource(R.string.unknown_character_field) },
            style = MaterialTheme.typography.bodyLarge,
            color = ToxicText
        )
    }
}
