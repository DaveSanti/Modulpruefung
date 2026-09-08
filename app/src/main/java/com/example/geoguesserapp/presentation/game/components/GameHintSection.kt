package com.example.geoguesserapp.presentation.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.geoguesserapp.R
import com.example.geoguesserapp.ui.theme.GeOdysseyBeige
import com.example.geoguesserapp.ui.theme.GeOdysseyNavyLight
import com.example.geoguesserapp.ui.theme.GeOdysseyTextDark
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Zeigt entweder den Hint Button oder den bereits verwendeten Hinweis.
// Die Component enthält keine Hint Logik, sondern meldet nur den Button Klick zurück.
@Composable
fun GameHintSection(
    hintUsed: Boolean,
    hint: String,
    hintPenalty: Int,
    onUseHint: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (hintUsed) {

        // Der Hint wird erst angezeigt, nachdem der Spieler ihn verwendet hat.
        // Danach benötigt der längere Hinweistext die volle verfügbare Breite.
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = GeOdysseyBeige
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = stringResource(R.string.game_hint_title),
                    color = GeOdysseyTextDark,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = hint,
                    color = GeOdysseyTextDark,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

    } else {

        // Vor der Verwendung bleibt nur der Button sichtbar.
        // Die Punkteabzüge werden nicht hier berechnet, sondern nur im Text angekündigt.
        Button(
            onClick = onUseHint,
            modifier = modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GeOdysseyNavyLight,
                contentColor = GeOdysseyWhite
            )
        ) {
            Text(
                text = stringResource(
                    R.string.game_hint_button,
                    hintPenalty
                ),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
