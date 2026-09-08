package com.example.geoguesserapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.geoguesserapp.ui.theme.GeOdysseyOrange
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Einheitlicher primärer Button für wichtige Aktionen innerhalb der App.
// Dadurch verwenden zentrale Aktionen auf allen Screens dieselbe Form, Höhe und Farbe.
@Composable
fun GeOdysseyPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    // Der übergebene Modifier wird zuerst angewendet,
    // damit Screens die Breite oder Position des Buttons selbst festlegen können.
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GeOdysseyOrange,
            contentColor = GeOdysseyWhite,
            // Deaktivierte Buttons bleiben sichtbar,
            // wirken aber deutlich weniger dominant als aktive Hauptaktionen.
            disabledContainerColor = GeOdysseyOrange.copy(alpha = 0.35f),
            disabledContentColor = GeOdysseyWhite.copy(alpha = 0.65f)
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
