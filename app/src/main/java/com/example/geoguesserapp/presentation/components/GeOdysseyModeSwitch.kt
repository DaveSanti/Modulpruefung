package com.example.geoguesserapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.geoguesserapp.R
import com.example.geoguesserapp.domain.model.GameMode
import com.example.geoguesserapp.ui.theme.GeOdysseyGreen
import com.example.geoguesserapp.ui.theme.GeOdysseyNavyLight
import com.example.geoguesserapp.ui.theme.GeOdysseyOrange
import com.example.geoguesserapp.ui.theme.GeOdysseyTextSecondary
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Wiederverwendbarer segmentierter Schalter für die Auswahl eines Spielmodus.
// Die beiden Optionen bilden eine gemeinsame Auswahl und werden gleich breit dargestellt.
@Composable
fun GeOdysseyModeSwitch(
    selectedMode: GameMode,
    onModeSelected: (GameMode) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(18.dp))
            .border(
                width = 1.dp,
                color = GeOdysseyTextSecondary,
                shape = RoundedCornerShape(18.dp)
            )
    ) {

        // Teilt die verfügbare Breite exakt auf beide Auswahlmöglichkeiten auf.
        val segmentWidth = maxWidth / 2

        Row(
            modifier = Modifier
                .fillMaxSize()
                // Die beiden Segmente werden semantisch als zusammengehörige Auswahl behandelt.
                // Screenreader können den Wechsel dadurch besser einordnen.
                .selectableGroup()
        ) {

            // Auswahl für den klassischen Modus.
            Box(
                modifier = Modifier
                    .width(segmentWidth)
                    .fillMaxSize()
                    .background(
                        if (selectedMode == GameMode.CLASSIC) {
                            GeOdysseyOrange
                        } else {
                            GeOdysseyNavyLight
                        }
                    )
                    .selectable(
                        selected = selectedMode == GameMode.CLASSIC,
                        onClick = {
                            onModeSelected(GameMode.CLASSIC)
                        },
                        role = Role.RadioButton
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.mode_classic),
                    color = GeOdysseyWhite,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }

            // Auswahl für den historischen Modus.
            Box(
                modifier = Modifier
                    .width(segmentWidth)
                    .fillMaxSize()
                    .background(
                        if (selectedMode == GameMode.HISTORICAL) {
                            GeOdysseyGreen
                        } else {
                            GeOdysseyNavyLight
                        }
                    )
                    .selectable(
                        selected = selectedMode == GameMode.HISTORICAL,
                        onClick = {
                            onModeSelected(GameMode.HISTORICAL)
                        },
                        role = Role.RadioButton
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.mode_historical),
                    color = GeOdysseyWhite,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
