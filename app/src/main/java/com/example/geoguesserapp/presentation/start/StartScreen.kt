package com.example.geoguesserapp.presentation.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geoguesserapp.R
import com.example.geoguesserapp.ui.theme.GeOdysseyNavy
import com.example.geoguesserapp.ui.theme.GeOdysseyOrange
import com.example.geoguesserapp.ui.theme.GeOdysseyTextSecondary
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Startscreen der App.
// Der Screen orientiert sich am dunklen GeOdyssey Design des UX Konzepts
// und ermöglicht beim ersten Start die Eingabe des Spielernamens.
@Composable
fun StartScreen(
    onContinue: (String) -> Unit
) {
    var playerName by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GeOdysseyNavy)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Zeigt auf dem Startscreen das vollständige GeOdyssey Logo an.
        // Das große Logo wird bewusst nur beim Einstieg in die App verwendet.
        Image(
            painter = painterResource(R.drawable.geodyssey_logo),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(380.dp)
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = stringResource(R.string.start_name_question),
            color = GeOdysseyWhite,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(R.string.start_name_description),
            color = GeOdysseyTextSecondary,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 24.dp
            )
        )

        // Eingabefeld für den Spielernamen.
        // Die Farben werden bewusst an den dunklen Hintergrund angepasst.
        OutlinedTextField(
            value = playerName,
            onValueChange = {
                playerName = it
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            label = {
                Text(stringResource(R.string.player_name))
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            shape = RoundedCornerShape(16.dp),
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

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // Orange kennzeichnet entsprechend dem UX Konzept die wichtigste Aktion.
        Button(
            onClick = {
                val cleanPlayerName = playerName.trim()

                if (cleanPlayerName.isNotBlank()) {
                    onContinue(cleanPlayerName)
                }
            },
            enabled = playerName.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GeOdysseyOrange,
                contentColor = Color.White,
                disabledContainerColor = GeOdysseyOrange.copy(
                    alpha = 0.35f
                )
            )
        ) {
            Text(
                text = stringResource(R.string.start_button),
                style = MaterialTheme.typography.labelLarge,
                fontSize = 18.sp
            )
        }

        Text(
            text = stringResource(R.string.start_adventure_hint),
            color = GeOdysseyTextSecondary,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}
