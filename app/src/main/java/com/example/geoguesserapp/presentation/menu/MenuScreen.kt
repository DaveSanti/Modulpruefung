package com.example.geoguesserapp.presentation.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.geoguesserapp.R
import com.example.geoguesserapp.presentation.components.GeOdysseyMenuCard
import com.example.geoguesserapp.ui.theme.GeOdysseyBeige
import com.example.geoguesserapp.ui.theme.GeOdysseyBlue
import com.example.geoguesserapp.ui.theme.GeOdysseyGreen
import com.example.geoguesserapp.ui.theme.GeOdysseyNavy
import com.example.geoguesserapp.ui.theme.GeOdysseyOrange
import com.example.geoguesserapp.ui.theme.GeOdysseyTextDark
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Hauptmenü von GeOdyssey.
// Die vier Hauptbereiche orientieren sich am UX Concept 2.0.
// Statt Hintergrundbildern werden bewusst klare Farbflächen verwendet.
@Composable
fun MenuScreen(
    onClassicMode: () -> Unit,
    onHistoricalMode: () -> Unit,
    onTutorial: () -> Unit,
    onLeaderboard: () -> Unit,
    onChangeName: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GeOdysseyNavy)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(
                horizontal = 20.dp,
                vertical = 24.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Zeigt im Hauptmenü nur noch den kompakten GeOdyssey Schriftzug an.
        Image(
            painter = painterResource(R.drawable.schriftzug),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth(0.74f)
                .height(142.dp)
        )

        // Klassischer Modus.
        // Orange kennzeichnet entsprechend dem UX Konzept die wichtigste Spielaktion.
        GeOdysseyMenuCard(
            title = stringResource(R.string.menu_classic_title),
            description = stringResource(R.string.menu_classic_description),
            containerColor = GeOdysseyOrange,
            contentColor = GeOdysseyWhite,
            onClick = onClassicMode
        )

        // Historischer Modus.
        // Grün unterscheidet ihn visuell vom klassischen Spielmodus.
        GeOdysseyMenuCard(
            title = stringResource(R.string.menu_historical_title),
            description = stringResource(R.string.menu_historical_description),
            containerColor = GeOdysseyGreen,
            contentColor = GeOdysseyWhite,
            onClick = onHistoricalMode
        )

        // Leaderboard.
        GeOdysseyMenuCard(
            title = stringResource(R.string.leaderboard_title),
            description = stringResource(R.string.menu_leaderboard_description),
            containerColor = GeOdysseyBlue,
            contentColor = GeOdysseyWhite,
            onClick = onLeaderboard
        )

        // Tutorial.
        GeOdysseyMenuCard(
            title = stringResource(R.string.tutorial_title),
            description = stringResource(R.string.menu_tutorial_description),
            containerColor = GeOdysseyBeige,
            contentColor = GeOdysseyTextDark,
            onClick = onTutorial
        )

        // Name ändern ist eine deutlich kleinere sekundäre Auswahl.
        // Die gleiche Component sorgt trotzdem für einen konsistenten Stil.
        GeOdysseyMenuCard(
            title = stringResource(R.string.change_name),
            containerColor = GeOdysseyBlue,
            contentColor = GeOdysseyWhite,
            onClick = onChangeName,
            compact = true,
            modifier = Modifier.fillMaxWidth(0.55f)
        )
    }
}
