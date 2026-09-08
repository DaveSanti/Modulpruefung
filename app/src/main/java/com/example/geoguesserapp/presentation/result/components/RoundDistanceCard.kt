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
import java.util.Locale

// Zeigt die Entfernung zwischen Guess und richtigem Standort an.
// Die Entfernung wurde bereits berechnet und wird hier nur deutsch formatiert.
@Composable
fun RoundDistanceCard(
    distanceKm: Double
) {
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(R.string.round_result_distance),
                color = GeOdysseyTextDark,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            // Locale.GERMANY sorgt für die deutsche Zahlendarstellung mit Komma.
            Text(
                text = stringResource(
                    R.string.round_result_distance_km,
                    String.format(
                        Locale.GERMANY,
                        "%.1f",
                        distanceKm
                    )
                ),
                color = GeOdysseyTextDark,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
