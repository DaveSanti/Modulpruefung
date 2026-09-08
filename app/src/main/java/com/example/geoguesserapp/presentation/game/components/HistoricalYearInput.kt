package com.example.geoguesserapp.presentation.game.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.geoguesserapp.R
import com.example.geoguesserapp.ui.theme.GeOdysseyGreen
import com.example.geoguesserapp.ui.theme.GeOdysseyNavyLight
import com.example.geoguesserapp.ui.theme.GeOdysseyOrange
import com.example.geoguesserapp.ui.theme.GeOdysseyTextSecondary
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Zeigt die Jahreseingabe für den historischen Modus.
// Die Umrechnung in negative Jahre bleibt im GameScreen, damit die Spielzustandslogik dort gebündelt bleibt.
@Composable
fun HistoricalYearInput(
    yearInput: String,
    isBeforeChrist: Boolean,
    onYearInputChange: (String) -> Unit,
    onBeforeChristChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = GeOdysseyNavyLight
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = stringResource(R.string.game_year_title),
                color = GeOdysseyWhite,
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = yearInput,
                onValueChange = onYearInputChange,
                label = {
                    Text(
                        stringResource(
                            R.string.game_year_label
                        )
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = GeOdysseyWhite,
                    unfocusedTextColor = GeOdysseyWhite,
                    focusedBorderColor = GeOdysseyOrange,
                    unfocusedBorderColor = GeOdysseyTextSecondary,
                    focusedLabelColor = GeOdysseyOrange,
                    unfocusedLabelColor = GeOdysseyTextSecondary,
                    cursorColor = GeOdysseyOrange
                )
            )

            // Der Spieler entscheidet separat,
            // ob das eingegebene Jahr vor oder nach Christus liegt.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                FilterChip(
                    selected = isBeforeChrist,
                    onClick = {
                        onBeforeChristChange(true)
                    },
                    label = {
                        Text(
                            text = stringResource(
                                R.string.game_before_christ
                            ),
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = GeOdysseyGreen,
                        selectedLabelColor = GeOdysseyWhite
                    )
                )

                FilterChip(
                    selected = !isBeforeChrist,
                    onClick = {
                        onBeforeChristChange(false)
                    },
                    label = {
                        Text(
                            text = stringResource(
                                R.string.game_after_christ
                            ),
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = GeOdysseyGreen,
                        selectedLabelColor = GeOdysseyWhite
                    )
                )
            }
        }
    }
}
