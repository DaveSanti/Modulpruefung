package com.example.geoguesserapp.presentation.game.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.geoguesserapp.R
import com.example.geoguesserapp.ui.theme.GeOdysseyOrange
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Zeigt den Button zum Bestätigen des Guesses.
// Ob der Button aktiv ist, entscheidet weiterhin der GameScreen anhand des Spielzustands.
@Composable
fun GameSubmitButton(
    enabled: Boolean,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Die Höhe bleibt fest, während die Breite vom aufrufenden Layout kommt.
    // Dadurch funktioniert der Button sowohl alleine als auch neben dem Hint Button.
    Button(
        onClick = onSubmit,
        enabled = enabled,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GeOdysseyOrange,
            contentColor = GeOdysseyWhite,
            // Der deaktivierte Zustand zeigt,
            // dass noch Standort oder historisches Jahr fehlen.
            disabledContainerColor = GeOdysseyOrange.copy(
                alpha = 0.35f
            ),
            disabledContentColor = GeOdysseyWhite.copy(
                alpha = 0.65f
            )
        )
    ) {
        Text(
            text = stringResource(R.string.game_submit_guess),
            style = MaterialTheme.typography.labelMedium
        )
    }
}
