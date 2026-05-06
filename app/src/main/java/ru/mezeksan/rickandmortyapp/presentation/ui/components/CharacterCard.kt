package ru.mezeksan.rickandmortyapp.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.mezeksan.rickandmortyapp.R
import ru.mezeksan.rickandmortyapp.domain.entity.Character
import ru.mezeksan.rickandmortyapp.ui.theme.PortalBlue
import ru.mezeksan.rickandmortyapp.ui.theme.PortalGreen
import ru.mezeksan.rickandmortyapp.ui.theme.Red
import ru.mezeksan.rickandmortyapp.ui.theme.SpaceCard
import ru.mezeksan.rickandmortyapp.ui.theme.ToxicText


@Composable
fun CharacterCard(
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