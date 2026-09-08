package com.example.geoguesserapp.presentation.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.geoguesserapp.R
import com.example.geoguesserapp.domain.model.GameMode
import com.example.geoguesserapp.presentation.components.GeOdysseyModeSwitch
import com.example.geoguesserapp.presentation.leaderboard.components.LeaderboardEntryCard
import com.example.geoguesserapp.ui.theme.GeOdysseyBlue
import com.example.geoguesserapp.ui.theme.GeOdysseyGreen
import com.example.geoguesserapp.ui.theme.GeOdysseyNavy
import com.example.geoguesserapp.ui.theme.GeOdysseyNavyLight
import com.example.geoguesserapp.ui.theme.GeOdysseyOrange
import com.example.geoguesserapp.ui.theme.GeOdysseyTextSecondary
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Zeigt das Online Leaderboard von GeOdyssey an.
// Über die beiden Filter kann zwischen klassischem und historischem Modus gewechselt werden.
// Die Einträge werden automatisch über das LeaderboardViewModel aktualisiert.
@Composable
fun LeaderboardScreen(
    leaderboardViewModel: LeaderboardViewModel,
    onBack: () -> Unit
) {
    val uiState by leaderboardViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GeOdysseyNavy)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Hauptüberschrift des Leaderboards.
        Text(
            text = stringResource(R.string.leaderboard_title),
            color = GeOdysseyWhite,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Kurze Erklärung unterhalb der Überschrift.
        Text(
            text = stringResource(R.string.leaderboard_subtitle),
            color = GeOdysseyTextSecondary,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Zusammenhängender Modusschalter für die beiden getrennten Leaderboards.
        // Die eigentliche Darstellung befindet sich als wiederverwendbare Component
        // im Package presentation.components.
        GeOdysseyModeSwitch(
            selectedMode = uiState.selectedMode,
            onModeSelected = { gameMode ->
                leaderboardViewModel.selectMode(gameMode)
            },
            modifier = Modifier.fillMaxWidth(0.82f)
        )

        // Zeigt zusätzlich als Text an, welches Leaderboard gerade aktiv ist.
        Text(
            text = when (uiState.selectedMode) {
                GameMode.CLASSIC -> stringResource(R.string.leaderboard_classic_title)
                GameMode.HISTORICAL -> stringResource(R.string.leaderboard_historical_title)
            },
            color = when (uiState.selectedMode) {
                GameMode.CLASSIC -> GeOdysseyOrange
                GameMode.HISTORICAL -> GeOdysseyGreen
            },
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Während Firebase Daten lädt, wird eine Ladeanzeige dargestellt.
        if (uiState.isLoading) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = GeOdysseyOrange
                )
            }

            // Wird angezeigt, wenn im ausgewählten Modus noch keine Highscores vorhanden sind.
        } else if (uiState.entries.isEmpty()) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = GeOdysseyNavyLight
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.leaderboard_empty),
                        color = GeOdysseyTextSecondary,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Zeigt alle Einträge des aktuell ausgewählten Leaderboards.
        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                itemsIndexed(
                    items = uiState.entries
                ) { index, entry ->

                    // Für die sichtbare Anzeige wird der Originalname verwendet.
                    // normalizedName bleibt nur für interne Vergleiche und Firebase Datenlogik relevant.
                    LeaderboardEntryCard(
                        position = index + 1,
                        playerName = entry.playerName,
                        playerTag = entry.playerTag,
                        score = entry.bestScore
                    )
                }
            }
        }

        // Führt zurück zum Hauptmenü.
        // Der Button ist bewusst schmaler als die Ranglisten Cards
        // und steht mittig unter dem Leaderboard.
        Button(
            onClick = onBack,
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .height(56.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GeOdysseyBlue,
                contentColor = GeOdysseyWhite
            )
        ) {
            Text(
                text = stringResource(R.string.leaderboard_back),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
