package com.example.geoguesserapp.presentation.tutorial

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.geoguesserapp.presentation.components.GeOdysseyModeSwitch
import com.example.geoguesserapp.ui.theme.GeOdysseyBeige
import com.example.geoguesserapp.ui.theme.GeOdysseyBlue
import com.example.geoguesserapp.ui.theme.GeOdysseyGreen
import com.example.geoguesserapp.ui.theme.GeOdysseyNavy
import com.example.geoguesserapp.ui.theme.GeOdysseyOrange
import com.example.geoguesserapp.ui.theme.GeOdysseyTextDark
import com.example.geoguesserapp.ui.theme.GeOdysseyTextSecondary
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite
import com.example.geoguesserapp.util.Constants

// Zeigt die Spielregeln für beide GeOdyssey Spielmodi an.
// Über die Filter kann jederzeit zwischen klassischem und historischem Tutorial gewechselt werden.
@Composable
fun TutorialScreen(
    onBack: () -> Unit
) {
    // Speichert, für welchen Spielmodus aktuell die Anleitung angezeigt wird.
    var selectedMode by remember {
        mutableStateOf(GameMode.CLASSIC)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GeOdysseyNavy)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 16.dp,
                vertical = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Hauptüberschrift des Tutorials.
        Text(
            text = stringResource(R.string.tutorial_title),
            color = GeOdysseyWhite,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = stringResource(R.string.tutorial_subtitle),
            color = GeOdysseyTextSecondary,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        // Zusammenhängender Schalter für die Auswahl der Tutorialregeln.
        // Die gleiche Component wird auch im Leaderboard verwendet.
        GeOdysseyModeSwitch(
            selectedMode = selectedMode,
            onModeSelected = { gameMode ->
                selectedMode = gameMode
            },
            modifier = Modifier.fillMaxWidth(0.82f)
        )

        // Zeigt abhängig von der aktuellen Auswahl die passenden Spielregeln an.
        when (selectedMode) {

            GameMode.CLASSIC -> {
                ClassicTutorialContent()
            }

            GameMode.HISTORICAL -> {
                HistoricalTutorialContent()
            }
        }

        // Führt zurück zum Hauptmenü.
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
                text = stringResource(R.string.tutorial_back),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Zeigt die Regeln des klassischen Spielmodus.
// Jede Regel befindet sich in einer eigenen Card, damit der Inhalt schnell erfassbar bleibt.
@Composable
private fun ClassicTutorialContent() {

    Text(
        text = stringResource(R.string.classic_mode),
        color = GeOdysseyOrange,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

    TutorialCard(
        title = stringResource(R.string.tutorial_classic_location_title),
        text = stringResource(R.string.tutorial_classic_location_text)
    )

    TutorialCard(
        title = stringResource(R.string.tutorial_classic_map_title),
        text = stringResource(R.string.tutorial_classic_map_text)
    )

    TutorialCard(
        title = stringResource(R.string.tutorial_classic_time_title),
        text = stringResource(R.string.tutorial_classic_time_text)
    )

    TutorialCard(
        title = stringResource(R.string.tutorial_classic_hint_title),
        text = stringResource(
            R.string.tutorial_classic_hint_text,
            Constants.HINT_PENALTY
        )
    )

    TutorialCard(
        title = stringResource(R.string.tutorial_classic_points_title),
        text = stringResource(
            R.string.tutorial_classic_points_text,
            Constants.MAX_POINTS_PER_ROUND
        )
    )
}

// Zeigt die Regeln des historischen Spielmodus.
// Der Aufbau entspricht bewusst dem klassischen Tutorial,
// damit beide Modi schnell miteinander verglichen werden können.
@Composable
private fun HistoricalTutorialContent() {

    Text(
        text = stringResource(R.string.historical_mode),
        color = GeOdysseyGreen,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

    TutorialCard(
        title = stringResource(R.string.tutorial_historical_location_title),
        text = stringResource(R.string.tutorial_historical_location_text)
    )

    TutorialCard(
        title = stringResource(R.string.tutorial_historical_map_title),
        text = stringResource(R.string.tutorial_historical_map_text)
    )

    TutorialCard(
        title = stringResource(R.string.tutorial_historical_year_title),
        text = stringResource(R.string.tutorial_historical_year_text)
    )

    TutorialCard(
        title = stringResource(R.string.tutorial_historical_time_title),
        text = stringResource(R.string.tutorial_historical_time_text)
    )

    TutorialCard(
        title = stringResource(R.string.tutorial_historical_hint_title),
        text = stringResource(
            R.string.tutorial_historical_hint_text,
            Constants.HINT_PENALTY
        )
    )

    TutorialCard(
        title = stringResource(R.string.tutorial_historical_points_title),
        text = stringResource(
            R.string.tutorial_historical_points_text,
            Constants.MAX_POINTS_PER_ROUND
        )
    )
}

// Wiederverwendbare Card für die Tutorialregeln.
// Die helle Fläche und dunkle Schrift sorgen für einen hohen Kontrast.
@Composable
private fun TutorialCard(
    title: String,
    text: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = GeOdysseyBeige
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
                text = title,
                color = GeOdysseyTextDark,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = text,
                color = GeOdysseyTextDark,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
