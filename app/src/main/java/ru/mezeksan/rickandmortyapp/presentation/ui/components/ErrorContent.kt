package ru.mezeksan.rickandmortyapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.mezeksan.rickandmortyapp.R
import ru.mezeksan.rickandmortyapp.presentation.state.UserErrorKind
import ru.mezeksan.rickandmortyapp.ui.theme.PortalGreen
import ru.mezeksan.rickandmortyapp.ui.theme.ToxicText

@Composable
fun ErrorContent(
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
