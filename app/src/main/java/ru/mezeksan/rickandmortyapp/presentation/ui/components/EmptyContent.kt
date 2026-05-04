package ru.mezeksan.rickandmortyapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.mezeksan.rickandmortyapp.R
import ru.mezeksan.rickandmortyapp.ui.theme.PortalGreen
import ru.mezeksan.rickandmortyapp.ui.theme.ToxicText

@Composable
fun EmptyContent(searchQuery: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (searchQuery.isNotBlank()) {
            Text(
                text = stringResource(R.string.empty_search_title),
                style = MaterialTheme.typography.headlineSmall,
                color = PortalGreen
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.empty_search_subtitle, searchQuery.trim()),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = ToxicText
            )
        } else {
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
}