package com.example.geoguesserapp.presentation.game

import com.example.geoguesserapp.domain.model.GameLocation
import com.example.geoguesserapp.domain.model.GameMode
import com.example.geoguesserapp.domain.model.GuessResult
import com.example.geoguesserapp.util.Constants

// In dieser Datenklasse wird der komplette aktuelle Zustand eines Spiels gespeichert.
// Das ViewModel verändert diesen Zustand und die Benutzeroberfläche kann darauf reagieren.
// Dadurch befindet sich der Spielzustand an einer zentralen Stelle und wird nicht
// über verschiedene Screens oder einzelne Variablen verteilt.

data class GameUiState(

    // Allgemeine Spielinformationen.
    val playerName: String = "", // Speichert den Namen des Spielers.
    val gameMode: GameMode? = null, // Speichert den ausgewählten Spielmodus.
    val locations: List<GameLocation> = emptyList(), // Enthält die 5 Standorte des aktuellen Spiels.
    val currentRound: Int = 0, // Speichert den aktuellen Guess als Listenindex.

    // Eingaben des Spielers in der aktuellen Runde.
    val selectedLatitude: Double? = null, // Speichert den Breitengrad des gesetzten Pins.
    val selectedLongitude: Double? = null, // Speichert den Längengrad des gesetzten Pins.
    val guessedYear: Int? = null, // Speichert die Jahreseingabe im historischen Modus.
    val hintUsed: Boolean = false, // Speichert, ob der Hint im aktuellen Guess verwendet wurde.

    // Zeit und Ergebnisdaten.
    val remainingTime: Int = Constants.CLASSIC_TIME_SECONDS, // Speichert die Restzeit im klassischen Modus.
    val results: List<GuessResult> = emptyList(), // Speichert die Ergebnisse aller bereits abgeschlossenen Guesses.
    val totalScore: Int = 0, // Speichert die bisher erreichte Gesamtpunktzahl.

    // Steuerung der sichtbaren Screens.
    val showRoundResult: Boolean = false, // Gibt an, ob nach einem Guess das Rundenergebnis angezeigt werden soll.
    val isGameFinished: Boolean = false, // Gibt an, ob alle 5 Guesses abgeschlossen wurden.
    val isPlayerLoaded: Boolean = false // Gibt an, ob der gespeicherte Spielername bereits geladen wurde.

)
