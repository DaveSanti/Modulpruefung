package com.example.geoguesserapp.presentation.result.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.geoguesserapp.R
import com.example.geoguesserapp.ui.theme.GeOdysseyBeige
import com.example.geoguesserapp.ui.theme.GeOdysseyTextDark

// Zeigt den Sonderfall an, wenn der Timer abgelaufen ist,
// bevor der Spieler einen Pin auf der Karte gesetzt hat.
@Composable
fun NoGuessCard() {
    // Der No Guess Fall wird als helle Warn-Card dargestellt,
    // damit klar ist, warum für diese Runde keine Distanzkarte erscheint.
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = GeOdysseyBeige
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Titel und Erklärung stehen zusammen,
            // weil sie denselben Sonderfall des Timer-Ablaufs beschreiben.
            Text(
                text = stringResource(R.string.round_result_no_guess_title),
                color = GeOdysseyTextDark,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(R.string.round_result_no_guess_text),
                color = GeOdysseyTextDark,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
