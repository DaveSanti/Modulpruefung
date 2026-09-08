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
import com.example.geoguesserapp.domain.model.GameLocation
import com.example.geoguesserapp.domain.model.HistoricalLocation
import com.example.geoguesserapp.ui.theme.GeOdysseyGreen
import com.example.geoguesserapp.ui.theme.GeOdysseyNavyLight
import com.example.geoguesserapp.ui.theme.GeOdysseyTextSecondary
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Zeigt den richtigen Standort der aktuellen Runde an.
// Im historischen Modus werden zusätzlich Jahr und Ereignis dargestellt.
@Composable
fun RoundLocationCard(
    location: GameLocation
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = GeOdysseyNavyLight
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.round_result_correct_location),
                color = GeOdysseyTextSecondary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Text(
                text = location.name,
                color = GeOdysseyWhite,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            // HistoricalLocation besitzt im Gegensatz zu normalen Standorten
            // zusätzliche Informationen über Jahr und historisches Ereignis.
            if (location is HistoricalLocation) {
                Text(
                    text = stringResource(
                        R.string.round_result_correct_year,
                        formatYear(location.year)
                    ),
                    color = GeOdysseyGreen,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = stringResource(
                        R.string.round_result_event,
                        location.event
                    ),
                    color = GeOdysseyWhite,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

// Formatiert historische Jahreszahlen für die sichtbare Benutzeroberfläche.
// Im Datenmodell werden Jahre vor Christus als negative Ganzzahlen gespeichert.
@Composable
private fun formatYear(
    year: Int
): String {
    val visibleYear = if (year < 0) {
        -year
    } else {
        year
    }

    val era = if (year < 0) {
        stringResource(R.string.game_before_christ)
    } else {
        stringResource(R.string.game_after_christ)
    }

    return "$visibleYear $era"
}
