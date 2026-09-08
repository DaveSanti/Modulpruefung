package com.example.geoguesserapp.presentation.result

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.geoguesserapp.ui.theme.GeOdysseyGreen
import com.example.geoguesserapp.ui.theme.GeOdysseyNavy
import com.example.geoguesserapp.ui.theme.GeOdysseyNavyLight
import com.example.geoguesserapp.ui.theme.GeOdysseyOrange
import com.example.geoguesserapp.ui.theme.GeOdysseyTextSecondary
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite
import com.example.geoguesserapp.util.Constants

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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 28.dp,
                    bottom = 24.dp
                ),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = GeOdysseyNavyLight
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 28.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Text(
                    text = stringResource(R.string.end_result_score_title),
                    color = GeOdysseyTextSecondary,
                    style = MaterialTheme.typography.titleMedium
                )

                // Große Scoreanzeige.
                Text(
                    text = totalScore.toString(),
                    color = GeOdysseyWhite,
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center
                )

                // Zeigt zusätzlich die maximal erreichbare Punktzahl.
                Text(
                    text = stringResource(
                        R.string.end_result_score_max,
                        Constants.MAX_POINTS_PER_GAME
                    ),
                    color = GeOdysseyTextSecondary,
                    style = MaterialTheme.typography.bodyLarge
                )

                // Textliche Bewertung des Ergebnisses.
                // Die Einschätzung wird bewusst nicht nur durch Farbe vermittelt.
                Text(
                    text = stringResource(
                        getScoreMessageRes(totalScore)
                    ),
                    color = when {
                        totalScore >= 18000 -> GeOdysseyGreen
                        totalScore >= 10000 -> GeOdysseyOrange
                        else -> GeOdysseyWhite
                    },
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

        // Primäre Aktion des Screens.
        // Orange entspricht der wichtigsten Aktion im GeOdyssey UX Konzept.
        Button(
            onClick = onPlayAgain,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GeOdysseyOrange,
                contentColor = GeOdysseyWhite
            )
        ) {
            Text(
                text = stringResource(R.string.end_result_play_again),
                style = MaterialTheme.typography.labelLarge
            )
        }

        // Die Rückkehr zum Menü ist eine sekundäre Aktion
        // und wird deshalb als umrandeter Button dargestellt.
        OutlinedButton(
            onClick = onBackToMenu,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .height(56.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = GeOdysseyWhite
            ),
            border = BorderStroke(
                width = 1.dp,
                color = GeOdysseyWhite
            )
        ) {
            Text(
                text = stringResource(R.string.end_result_back_to_menu),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

// Gibt abhängig von der erreichten Gesamtpunktzahl den passenden Text zurück.
// Die Bewertung dient nur der Ergebnisdarstellung und beeinflusst das Leaderboard nicht.
private fun getScoreMessageRes(
    totalScore: Int
): Int {
    return when {
        totalScore >= 22000 -> R.string.end_result_score_excellent
        totalScore >= 18000 -> R.string.end_result_score_very_good
        totalScore >= 14000 -> R.string.end_result_score_good
        totalScore >= 10000 -> R.string.end_result_score_solid
        else -> R.string.end_result_score_try_again
    }
}
