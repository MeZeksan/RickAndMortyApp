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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import ru.mezeksan.rickandmortyapp.R
import ru.mezeksan.rickandmortyapp.domain.entity.CharacterDetail
import ru.mezeksan.rickandmortyapp.presentation.state.CharacterDetailUiState
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
        viewModel.loadCharacter(characterId)
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
                        onRetry = { viewModel.loadCharacter(characterId) }
                    )
                }
                is CharacterDetailUiState.Success -> {
                    CharacterDetailContent(character = state.character)
                }
            }
        }
    }
}

@Composable
fun CharacterDetailContent(character: CharacterDetail) {
    val statusColor = when (character.status.lowercase()) {
        "alive" -> PortalGreen
        "dead" -> Red
        else -> PortalBlue
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
