package com.example.geoguesserapp.presentation.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.geoguesserapp.R
import com.example.geoguesserapp.domain.model.GameMode
import com.example.geoguesserapp.presentation.components.GeOdysseyHighscoreDialog
import com.example.geoguesserapp.presentation.result.components.EndResultActions
import com.example.geoguesserapp.presentation.result.components.EndResultScoreCard
import com.example.geoguesserapp.ui.theme.GeOdysseyGreen
import com.example.geoguesserapp.ui.theme.GeOdysseyNavy
import com.example.geoguesserapp.ui.theme.GeOdysseyOrange
import com.example.geoguesserapp.ui.theme.GeOdysseyTextSecondary
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Zeigt nach Abschluss aller fünf Runden das Gesamtergebnis des Spiels an.
// Der Screen zeigt den Spielmodus, die Gesamtpunktzahl und bietet die Möglichkeit,
// erneut zu spielen oder zum Hauptmenü zurückzukehren.
@Composable
fun EndResultScreen(
    totalScore: Int,
    gameMode: GameMode?,
    isNewPersonalHighscore: Boolean,
    onPlayAgain: () -> Unit,
    onBackToMenu: () -> Unit
) {
    var showHighscoreDialog by remember(isNewPersonalHighscore) {
        mutableStateOf(isNewPersonalHighscore)
    }

    // Das Trophy Popup erscheint nur, wenn das Repository einen neuen Bestwert bestätigt.
    if (showHighscoreDialog) {
        GeOdysseyHighscoreDialog(
            closeText = stringResource(R.string.continue_button),
            contentDescription = stringResource(
                R.string.highscore_trophy_description
            ),
            onDismiss = {
                showHighscoreDialog = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GeOdysseyNavy)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Hauptüberschrift des Ergebnisscreens.
        Text(
            text = stringResource(R.string.end_result_title),
            color = GeOdysseyWhite,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        // Zeigt den gespielten Modus zusätzlich als Text an.
        // Dadurch wird die Information nicht nur durch Farben vermittelt.
        Text(
            text = when (gameMode) {
                GameMode.CLASSIC -> stringResource(R.string.classic_mode)
                GameMode.HISTORICAL -> stringResource(R.string.historical_mode)
                null -> ""
            },
            color = when (gameMode) {
                GameMode.CLASSIC -> GeOdysseyOrange
                GameMode.HISTORICAL -> GeOdysseyGreen
                null -> GeOdysseyTextSecondary
            },
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        // Die Gesamtpunktzahl ist entsprechend dem UX Konzept
        // das visuell wichtigste Element des Screens.
        // Die Darstellung liegt in einer eigenen Component,
        // damit dieser Screen nur noch den Ablauf des Endergebnisses beschreibt.
        EndResultScoreCard(
            totalScore = totalScore
        )

        // Bündelt die beiden Abschlussaktionen des Screens.
        // Die Callbacks bleiben unverändert und werden nur weitergereicht.
        EndResultActions(
            onPlayAgain = onPlayAgain,
            onBackToMenu = onBackToMenu
        )
    }
}
