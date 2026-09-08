package com.example.geoguesserapp.presentation.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.geoguesserapp.R
import com.example.geoguesserapp.domain.model.GameMode
import com.example.geoguesserapp.ui.theme.GeOdysseyNavyLight
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Zeigt das Bild als zentrale Informationsquelle des Guesses.
// Nur historische Bilder melden einen Klick für die Vollbildansicht zurück.
@Composable
fun GameImageCard(
    imageResId: Int,
    gameMode: GameMode?,
    onHistoricalImageClick: () -> Unit
) {
    if (imageResId != 0) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = GeOdysseyNavyLight
            )
        ) {
            Image(
                painter = painterResource(imageResId),

                // Die Beschreibung verrät bewusst nicht den gesuchten Ort.
                // Dadurch bleibt der Spielinhalt auch mit Screenreader ungelöst.
                contentDescription = stringResource(
                    R.string.game_image_description
                ),

                contentScale = ContentScale.Crop,

                // Nur historische Bilder können durch Antippen
                // in der Vollbildansicht geöffnet werden.
                modifier = if (
                    gameMode == GameMode.HISTORICAL
                ) {
                    Modifier
                        .fillMaxSize()
                        .clickable {
                            onHistoricalImageClick()
                        }
                } else {
                    Modifier.fillMaxSize()
                }
            )
        }

    } else {

        Text(
            text = stringResource(R.string.game_image_error),
            color = GeOdysseyWhite,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
