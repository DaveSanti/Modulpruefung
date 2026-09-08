package com.example.geoguesserapp.presentation.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.geoguesserapp.R
import com.example.geoguesserapp.domain.model.GuessResult
import com.example.geoguesserapp.presentation.result.components.NoGuessCard
import com.example.geoguesserapp.presentation.result.components.RoundDistanceCard
import com.example.geoguesserapp.presentation.result.components.RoundLocationCard
import com.example.geoguesserapp.presentation.result.components.RoundPointsCard
import com.example.geoguesserapp.presentation.result.components.RoundResultMap
import com.example.geoguesserapp.ui.theme.GeOdysseyNavy
import com.example.geoguesserapp.ui.theme.GeOdysseyOrange
import com.example.geoguesserapp.ui.theme.GeOdysseyTextSecondary
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Zeigt nach jedem Guess das Ergebnis der aktuellen Runde.
// Der Screen entscheidet nur noch, welche Ergebnisbereiche angezeigt werden.
// Die einzelnen Cards und die Ergebniskarte liegen in eigenen Components.
@Composable
fun RoundResultScreen(
    result: GuessResult,
    onContinue: () -> Unit
) {
    // Prüft, ob tatsächlich ein Standort Guess abgegeben wurde.
    // Im klassischen Modus kann die Zeit ablaufen, ohne dass vorher ein Pin gesetzt wurde.
    // In diesem Fall bleiben guessedLatitude, guessedLongitude und distanceKm null.
    val guessWasSubmitted =
        result.guessedLatitude != null &&
                result.guessedLongitude != null &&
                result.distanceKm != null

    // Der gesamte Ergebnisscreen ist scrollbar.
    // Dadurch bleibt die Oberfläche auch auf kleineren Geräten vollständig erreichbar.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GeOdysseyNavy)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Hauptüberschrift des Rundenergebnisses.
        Text(
            text = stringResource(R.string.round_result_title),
            color = GeOdysseyWhite,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        RoundLocationCard(
            location = result.location
        )

        // Bei einem abgegebenen Guess werden Entfernung, Punkte und Ergebnis-Karte angezeigt.
        // Ohne Pin wird stattdessen der No Guess Bereich dargestellt.
        if (guessWasSubmitted) {
            val guessedLatitude = result.guessedLatitude
            val guessedLongitude = result.guessedLongitude
            val distanceKm = result.distanceKm

            RoundDistanceCard(
                distanceKm = distanceKm
            )

            // Die Punkte wurden bereits im GameViewModel berechnet.
            // Diese Component stellt den Wert nur dar.
            RoundPointsCard(
                points = result.totalPoints
            )

            RoundResultMap(
                guessedLatitude = guessedLatitude,
                guessedLongitude = guessedLongitude,
                correctLatitude = result.location.latitude,
                correctLongitude = result.location.longitude
            )

            // Erklärt zusätzlich in Textform, welcher Pin welche Bedeutung besitzt.
            // Dadurch wird die Information nicht ausschließlich über Orange und Grün vermittelt.
            Text(
                text = stringResource(R.string.round_result_map_legend),
                color = GeOdysseyTextSecondary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

        } else {
            NoGuessCard()

            // Auch bei einem nicht abgegebenen Guess werden 0 Punkte erreicht.
            // Dadurch bleibt die Darstellung der Rundenergebnisse konsistent.
            RoundPointsCard(
                points = result.totalPoints,
                titleColor = GeOdysseyTextSecondary,
                modifier = Modifier.fillMaxWidth(0.7f)
            )
        }

        // Führt entweder zur nächsten Runde oder nach dem fünften Guess
        // zum Gesamtergebnis des Spiels.
        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .height(56.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GeOdysseyOrange,
                contentColor = GeOdysseyWhite
            )
        ) {
            Text(
                text = stringResource(R.string.continue_button),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
