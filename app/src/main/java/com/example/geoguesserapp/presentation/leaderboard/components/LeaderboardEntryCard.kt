package com.example.geoguesserapp.presentation.leaderboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.geoguesserapp.R
import com.example.geoguesserapp.ui.theme.GeOdysseyBeige
import com.example.geoguesserapp.ui.theme.GeOdysseyNavyLight
import com.example.geoguesserapp.ui.theme.GeOdysseyTextDark
import com.example.geoguesserapp.ui.theme.GeOdysseyTextSecondary
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Zeigt einen einzelnen Eintrag des Leaderboards.
// Die ersten drei Plätze erhalten zusätzlich eine Medaille und eine größere Card.
@Composable
fun LeaderboardEntryCard(
    position: Int,
    playerName: String,
    playerTag: String,
    score: Int
) {
    // Der erste Platz erhält eine helle Card.
    // Die restlichen Einträge bleiben auf der dunklen Standardoberfläche.
    val containerColor = when (position) {
        1 -> GeOdysseyBeige
        else -> GeOdysseyNavyLight
    }

    // Auf der hellen Card des ersten Platzes wird dunkle Schrift verwendet.
    val contentColor = when (position) {
        1 -> GeOdysseyTextDark
        else -> GeOdysseyWhite
    }

    // Die ersten drei Plätze werden größer dargestellt.
    // Ab Platz 4 werden die Einträge bewusst kompakter.
    val cardHeight = when (position) {
        1 -> 116.dp
        2 -> 104.dp
        3 -> 96.dp
        else -> 72.dp
    }

    val medalSize = when (position) {
        1 -> 64.dp
        2 -> 56.dp
        3 -> 52.dp
        else -> 0.dp
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight),
        shape = RoundedCornerShape(
            if (position <= 3) {
                22.dp
            } else {
                16.dp
            }
        ),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 16.dp,
                    vertical = if (position <= 3) 12.dp else 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Linker Bereich mit Medaille beziehungsweise Platznummer und Spielername.
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                if (position <= 3) {
                    Image(
                        painter = painterResource(
                            id = when (position) {
                                1 -> R.drawable.goldmedal
                                2 -> R.drawable.silvermedal
                                else -> R.drawable.bronzemedal
                            }
                        ),
                        contentDescription = when (position) {
                            1 -> stringResource(R.string.leaderboard_first_place)
                            2 -> stringResource(R.string.leaderboard_second_place)
                            else -> stringResource(R.string.leaderboard_third_place)
                        },
                        modifier = Modifier.size(medalSize)
                    )
                } else {
                    Text(
                        text = stringResource(
                            R.string.leaderboard_position,
                            position
                        ),
                        color = contentColor,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(
                            R.string.leaderboard_player_tag,
                            playerName,
                            playerTag
                        ),
                        color = contentColor,
                        style = if (position <= 3) {
                            MaterialTheme.typography.titleLarge
                        } else {
                            MaterialTheme.typography.titleMedium
                        },
                        fontWeight = FontWeight.Bold
                    )

                    if (position <= 3) {
                        Text(
                            text = when (position) {
                                1 -> stringResource(R.string.leaderboard_first_place)
                                2 -> stringResource(R.string.leaderboard_second_place)
                                else -> stringResource(R.string.leaderboard_third_place)
                            },
                            color = if (position == 1) {
                                GeOdysseyTextDark
                            } else {
                                GeOdysseyTextSecondary
                            },
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            // Die Punktzahl bleibt immer rechtsbündig.
            Text(
                text = stringResource(
                    R.string.leaderboard_score,
                    score
                ),
                color = contentColor,
                style = if (position <= 3) {
                    MaterialTheme.typography.headlineMedium
                } else {
                    MaterialTheme.typography.titleLarge
                },
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}
