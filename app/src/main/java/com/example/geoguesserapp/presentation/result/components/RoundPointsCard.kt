package com.example.geoguesserapp.presentation.result.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.geoguesserapp.R
import com.example.geoguesserapp.ui.theme.GeOdysseyNavyLight
import com.example.geoguesserapp.ui.theme.GeOdysseyOrange
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Zeigt die in dieser Runde erreichte Gesamtpunktzahl an.
// Der Wert wurde bereits im GameViewModel berechnet und wird hier nur dargestellt.
@Composable
fun RoundPointsCard(
    points: Int,
    modifier: Modifier = Modifier,
    titleColor: Color = GeOdysseyWhite
) {
    // Der Modifier wird von außen gesetzt,
    // damit dieselbe Punkte-Card im normalen und im No Guess Fall verwendet werden kann.
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = GeOdysseyNavyLight
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 14.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Die Überschriftsfarbe ist variabel,
            // damit der No Guess Fall optisch etwas zurückhaltender dargestellt werden kann.
            Text(
                text = stringResource(R.string.round_result_points),
                color = titleColor,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(
                    R.string.round_result_points_value,
                    points
                ),
                color = GeOdysseyOrange,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
