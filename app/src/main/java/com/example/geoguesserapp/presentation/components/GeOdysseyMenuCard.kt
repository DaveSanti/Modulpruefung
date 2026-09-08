package com.example.geoguesserapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// Wiederverwendbare Auswahlkarte für das Hauptmenü.
// Titel und Beschreibung werden bewusst zentriert dargestellt.
@Composable
fun GeOdysseyMenuCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit,
    compact: Boolean = false
) {
    // compact reduziert Höhe, Rundung und Innenabstände für Nebenaktionen.
    // Die großen Modus- und Navigationskarten bleiben dadurch visuell wichtiger.
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(
                if (compact) {
                    56.dp
                } else {
                    112.dp
                }
            ),
        shape = RoundedCornerShape(
            if (compact) {
                16.dp
            } else {
                24.dp
            }
        ),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {

        // Der Inhalt nutzt genau die feste Höhe der Card
        // und kann dadurch zuverlässig horizontal und vertikal zentriert werden.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = if (compact) 16.dp else 24.dp,
                    vertical = if (compact) 8.dp else 12.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = title,
                color = contentColor,
                style = if (compact) {
                    MaterialTheme.typography.titleMedium
                } else {
                    MaterialTheme.typography.titleLarge
                },
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            if (!description.isNullOrBlank()) {
                Text(
                    text = description,
                    color = contentColor.copy(alpha = 0.88f),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                )
            }
        }
    }
}
