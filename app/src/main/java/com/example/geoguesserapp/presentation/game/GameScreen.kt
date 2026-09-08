package com.example.geoguesserapp.presentation.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.geoguesserapp.R
import com.example.geoguesserapp.domain.model.GameMode
import com.example.geoguesserapp.presentation.components.GeOdysseyFullscreenImage
import com.example.geoguesserapp.presentation.game.components.GameHeader
import com.example.geoguesserapp.presentation.game.components.GameHintSection
import com.example.geoguesserapp.presentation.game.components.GameImageCard
import com.example.geoguesserapp.presentation.game.components.GameMapSection
import com.example.geoguesserapp.presentation.game.components.GameSubmitButton
import com.example.geoguesserapp.presentation.game.components.HistoricalYearInput
import com.example.geoguesserapp.ui.theme.GeOdysseyNavy
import com.example.geoguesserapp.util.Constants
import org.maplibre.compose.camera.CameraPosition
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.spatialk.geojson.Position

// Zeigt den aktuell laufenden Guess an.
// Der Screen liest den GameUiState aus dem GameViewModel und setzt die einzelnen
// UI Bereiche in der gleichen Reihenfolge wie vor dem Refactoring zusammen.
// Das Styling orientiert sich am dunklen GeOdyssey UX Konzept.
@Composable
fun GameScreen(
    gameViewModel: GameViewModel
) {
    val uiState by gameViewModel.uiState.collectAsStateWithLifecycle()
    val currentLocation = uiState.locations.getOrNull(uiState.currentRound) ?: return

    // Speichert die sichtbare Jahreseingabe im historischen Modus.
    // Der State bleibt hier, damit der Screen die Spielrunde weiterhin orchestriert.
    var yearInput by remember(uiState.currentRound) {
        mutableStateOf("")
    }

    // Speichert die Auswahl zwischen vor und nach Christus.
    // Die Umrechnung in das interne Jahresformat bleibt unverändert.
    var isBeforeChrist by remember(uiState.currentRound) {
        mutableStateOf(false)
    }

    // Speichert, ob das historische Bild aktuell in der Vollbildansicht geöffnet ist.
    // Die ausgelagerte Bildkarte meldet nur den Klick zurück.
    var showFullscreenImage by remember(uiState.currentRound) {
        mutableStateOf(false)
    }

    val scrollState = rememberScrollState()

    // Während der Karteninteraktion wird der äußere Scroll deaktiviert.
    // Dadurch konkurrieren Compose Scroll Gesten nicht mit MapLibre Gesten.
    var isMapInteracting by remember {
        mutableStateOf(false)
    }

    // Legt die Startansicht der Karte fest.
    // Die Karte beginnt ungefähr als Weltansicht ohne Drehung oder Neigung.
    val cameraState = rememberCameraState(
        firstPosition = CameraPosition(
            target = Position(
                longitude = 10.0,
                latitude = 20.0
            ),
            zoom = 1.3,
            bearing = 0.0,
            tilt = 0.0
        )
    )

    // Setzt die Ansicht zu Beginn jeder neuen Runde zurück.
    // Dadurch startet jeder Guess wieder am Seitenanfang
    // und die Karte beginnt erneut in der vorgesehenen Weltansicht.
    LaunchedEffect(uiState.currentRound) {

        // Springt beim Wechsel in die nächste Runde wieder ganz nach oben.
        scrollState.scrollTo(0)

        // Setzt die Karte für den neuen Guess wieder auf die Weltansicht zurück.
        cameraState.position = CameraPosition(
            target = Position(
                longitude = 10.0,
                latitude = 20.0
            ),
            zoom = 1.3,
            bearing = 0.0,
            tilt = 0.0
        )
    }

    // Öffnet die Vollbildansicht ausschließlich im historischen Modus.
    if (
        showFullscreenImage &&
        uiState.gameMode == GameMode.HISTORICAL &&
        currentLocation.imageResId != 0
    ) {
        GeOdysseyFullscreenImage(
            imageResId = currentLocation.imageResId,
            contentDescription = stringResource(
                R.string.game_fullscreen_image_description
            ),
            closeText = stringResource(
                R.string.game_fullscreen_close
            ),
            onDismiss = {
                showFullscreenImage = false
            }
        )
    }

    // Der Guess kann erst bestätigt werden, wenn ein Standort ausgewählt wurde.
    // Im historischen Modus muss zusätzlich ein Jahr vorhanden sein.
    val canSubmitGuess =
        uiState.selectedLatitude != null &&
                uiState.selectedLongitude != null &&
                (
                        uiState.gameMode != GameMode.HISTORICAL ||
                                uiState.guessedYear != null
                        )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GeOdysseyNavy)
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(
                state = scrollState,
                enabled = !isMapInteracting
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        GameHeader(
            currentRound = uiState.currentRound,
            gameMode = uiState.gameMode,
            remainingTime = uiState.remainingTime,
            totalRounds = Constants.ROUNDS_PER_GAME
        )

        GameImageCard(
            imageResId = currentLocation.imageResId,
            gameMode = uiState.gameMode,
            onHistoricalImageClick = {
                showFullscreenImage = true
            }
        )

        if (uiState.hintUsed) {

            // Nach Verwendung des Hints benötigt der Hinweistext die volle Breite.
            // Der Submit Button wird deshalb direkt darunter angezeigt.
            GameHintSection(
                hintUsed = true,
                hint = currentLocation.hint,
                hintPenalty = Constants.HINT_PENALTY,
                onUseHint = {
                    gameViewModel.useHint()
                }
            )

            GameSubmitButton(
                enabled = canSubmitGuess,
                onSubmit = {
                    gameViewModel.submitGuess()
                },
                modifier = Modifier.fillMaxWidth()
            )
        } else {

            // Solange noch kein Hint verwendet wurde,
            // werden Hint und Guess bestätigen direkt nebeneinander angezeigt.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                GameHintSection(
                    hintUsed = false,
                    hint = currentLocation.hint,
                    hintPenalty = Constants.HINT_PENALTY,
                    onUseHint = {
                        gameViewModel.useHint()
                    },
                    modifier = Modifier.weight(1f)
                )

                // Der Guess kann deaktiviert sein,
                // solange Standort oder historisches Jahr noch fehlen.
                GameSubmitButton(
                    enabled = canSubmitGuess,
                    onSubmit = {
                        gameViewModel.submitGuess()
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        if (uiState.gameMode == GameMode.HISTORICAL) {
            HistoricalYearInput(
                yearInput = yearInput,
                isBeforeChrist = isBeforeChrist,
                onYearInputChange = { input ->
                    if (input.all { it.isDigit() }) {
                        yearInput = input

                        updateInternalYear(
                            yearInput = yearInput,
                            isBeforeChrist = isBeforeChrist,
                            gameViewModel = gameViewModel
                        )
                    }
                },
                onBeforeChristChange = { beforeChrist ->
                    isBeforeChrist = beforeChrist

                    updateInternalYear(
                        yearInput = yearInput,
                        isBeforeChrist = beforeChrist,
                        gameViewModel = gameViewModel
                    )
                }
            )
        }

        GameMapSection(
            cameraState = cameraState,
            selectedLatitude = uiState.selectedLatitude,
            selectedLongitude = uiState.selectedLongitude,
            onLocationSelected = { latitude, longitude ->
                gameViewModel.selectLocation(
                    latitude = latitude,
                    longitude = longitude
                )
            },
            onMapInteractionChanged = { isInteracting ->
                isMapInteracting = isInteracting
            }
        )

    }
}

// Überträgt die sichtbare Jahreseingabe in das interne Format.
// Beispiel: 1200 v. Chr. wird intern als -1200 gespeichert.
private fun updateInternalYear(
    yearInput: String,
    isBeforeChrist: Boolean,
    gameViewModel: GameViewModel
) {
    val year = yearInput.toIntOrNull() ?: return

    val internalYear =
        if (isBeforeChrist) {
            -year
        } else {
            year
        }

    gameViewModel.setGuessedYear(
        internalYear
    )
}
