package ru.mezeksan.rickandmortyapp.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import ru.mezeksan.rickandmortyapp.R
import ru.mezeksan.rickandmortyapp.domain.entity.Character
import ru.mezeksan.rickandmortyapp.presentation.intent.CharacterListIntent
import ru.mezeksan.rickandmortyapp.presentation.state.CharacterListState
import ru.mezeksan.rickandmortyapp.presentation.state.UserErrorKind
import ru.mezeksan.rickandmortyapp.presentation.viewmodel.CharacterListViewModel
import ru.mezeksan.rickandmortyapp.ui.theme.PortalBlue
import ru.mezeksan.rickandmortyapp.ui.theme.PortalGreen
import ru.mezeksan.rickandmortyapp.ui.theme.Red
import ru.mezeksan.rickandmortyapp.ui.theme.SpaceCard
import ru.mezeksan.rickandmortyapp.ui.theme.ToxicText

@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

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
    ) {
        when (val currentState = state) {
            is CharacterListState.Loading -> LoadingContent()
            is CharacterListState.Success -> CharacterList(characters = currentState.characters)
            is CharacterListState.Error -> ErrorContent(
                kind = currentState.kind,
                onRetry = { viewModel.processIntent(CharacterListIntent.Retry) }
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(color = PortalGreen)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(R.string.loading_portal),
            color = ToxicText,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun CharacterList(characters: ImmutableList<Character>) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.character_list_title),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 8.dp),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = PortalGreen
        )
        if (characters.isEmpty()) {
            EmptyContent()
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(characters, key = { it.id }) { character ->
                    val unknown = stringResource(R.string.unknown_character_field)
                    CharacterCard(
                        character = character,
                        photoContentDescription = stringResource(
                            R.string.character_photo_content_description,
                            character.name.ifBlank { unknown }
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.empty_portal_title),
            style = MaterialTheme.typography.headlineSmall,
            color = PortalGreen
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.empty_portal_subtitle),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = ToxicText
        )
    }
}

@Composable
private fun CharacterCard(
    character: Character,
    photoContentDescription: String
) {
    val statusColor = when (character.status.lowercase()) {
        "alive" -> PortalGreen
        "dead" -> Red
        else -> PortalBlue
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = PortalBlue.copy(alpha = 0.5f),
                shape = RoundedCornerShape(18.dp)
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SpaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = photoContentDescription,
                placeholder = painterResource(id = R.drawable.ic_character_placeholder),
                error = painterResource(id = R.drawable.ic_character_placeholder),
                fallback = painterResource(id = R.drawable.ic_character_placeholder),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .padding(end = 12.dp)
            )
            Column {
                val unknown = stringResource(R.string.unknown_character_field)
                Text(
                    text = character.name.ifBlank { unknown },
                    style = MaterialTheme.typography.titleMedium,
                    color = ToxicText,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = character.species.ifBlank { unknown },
                    style = MaterialTheme.typography.bodyMedium,
                    color = PortalBlue
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = character.status.ifBlank { unknown },
                    style = MaterialTheme.typography.bodySmall,
                    color = statusColor
                )
            }
        }
    }
}

@Composable
private fun ErrorContent(
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
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.error_title),
            style = MaterialTheme.typography.headlineSmall,
            color = PortalGreen
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(messageRes),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = ToxicText
        )
        Spacer(modifier = Modifier.height(24.dp))
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
