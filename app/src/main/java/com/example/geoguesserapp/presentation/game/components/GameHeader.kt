package com.example.geoguesserapp.presentation.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.geoguesserapp.R
import com.example.geoguesserapp.domain.model.GameMode
import com.example.geoguesserapp.ui.theme.GeOdysseyGreen
import com.example.geoguesserapp.ui.theme.GeOdysseyOrange
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Zeigt die aktuelle Runde und im klassischen Modus zusätzlich die verbleibende Zeit.
// Im historischen Modus wird statt des Timers der Modus als Text angezeigt.
@Composable
fun GameHeader(
    currentRound: Int,
    gameMode: GameMode?,
    remainingTime: Int,
    totalRounds: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(
                R.string.game_round_counter,
                currentRound + 1,
                totalRounds
            ),
            color = GeOdysseyWhite,
            style = MaterialTheme.typography.titleMedium
        )

        if (gameMode == GameMode.CLASSIC) {

            // Die verbleibende Zeit bleibt immer als Zahl sichtbar.
            // Orange verstärkt lediglich die Warnwirkung in den letzten 10 Sekunden.
            Text(
                text = stringResource(
                    R.string.game_remaining_time,
                    remainingTime
                ),
                color = if (remainingTime <= 10) {
                    GeOdysseyOrange
                } else {
                    GeOdysseyWhite
                },
                style = MaterialTheme.typography.titleMedium
            )

        } else {

            // Der historische Modus besitzt keinen Timer.
            Text(
                text = stringResource(R.string.mode_historical),
                color = GeOdysseyGreen,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
