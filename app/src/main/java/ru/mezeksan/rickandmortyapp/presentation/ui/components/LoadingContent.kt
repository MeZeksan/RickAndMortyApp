package ru.mezeksan.rickandmortyapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mezeksan.rickandmortyapp.R
import ru.mezeksan.rickandmortyapp.ui.theme.PortalGreen
import ru.mezeksan.rickandmortyapp.ui.theme.ToxicText

@Composable
fun LoadingContent() {
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