package com.example.geoguesserapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.geoguesserapp.R
import com.example.geoguesserapp.ui.theme.GeOdysseyNavyLight
import com.example.geoguesserapp.ui.theme.GeOdysseyOrange
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Zeigt ein Trophy Popup, wenn ein neuer persönlicher Highscore erreicht wurde.
// Die Component ist allgemein gehalten und bekommt Text sowie Beschreibung von außen,
// damit sie keine eigene Ergebnis- oder Leaderboard Logik kennen muss.
@Composable
fun GeOdysseyHighscoreDialog(
    closeText: String,
    contentDescription: String,
    onDismiss: () -> Unit
) {
    // Der Dialog liegt über dem EndResultScreen und kann durch den Button geschlossen werden.
    // onDismiss wird vom aufrufenden Screen verwaltet.
    Dialog(
        onDismissRequest = onDismiss
    ) {
        // Die Card nutzt dieselbe dunkle Oberfläche wie andere wichtige GeOdyssey Bereiche.
        // Dadurch wirkt das Popup wie ein Teil der App und nicht wie ein Systemdialog.
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = GeOdysseyNavyLight
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Die Trophäe ist der zentrale visuelle Hinweis auf den neuen Bestwert.
                // Die contentDescription wird von außen übergeben, damit Accessibility erhalten bleibt.
                Image(
                    painter = painterResource(R.drawable.trophy),
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(240.dp)
                )

                // Der Button bestätigt das Popup und führt nicht selbst weiter durch die App.
                // Die Navigation bleibt deshalb außerhalb dieser wiederverwendbaren Component.
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GeOdysseyOrange,
                        contentColor = GeOdysseyWhite
                    )
                ) {
                    Text(
                        text = closeText,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}
