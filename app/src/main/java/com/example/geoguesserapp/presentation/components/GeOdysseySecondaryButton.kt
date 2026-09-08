package com.example.geoguesserapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Einheitlicher sekundärer Button für weniger dominante Aktionen.
// Er wird zum Beispiel für Zurück oder Zurück zum Menü verwendet.
@Composable
fun GeOdysseySecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Sekundäre Aktionen behalten dieselbe Höhe wie primäre Buttons,
    // werden aber nur über Kontur und Textfarbe hervorgehoben.
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = GeOdysseyWhite
        ),
        // Die dünne Kontur macht den Button erkennbar,
        // ohne ihn visuell wichtiger als die Hauptaktion wirken zu lassen.
        border = BorderStroke(
            width = 1.dp,
            color = GeOdysseyWhite
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
