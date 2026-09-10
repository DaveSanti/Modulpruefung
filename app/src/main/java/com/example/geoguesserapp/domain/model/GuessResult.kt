package com.example.geoguesserapp.domain.model

// Diese Datenklasse speichert das Ergebnis eines einzelnen Guesses.
// Die Ergebnisse werden während des Spiels gesammelt und erst nach dem fünften Guess angezeigt.
data class GuessResult(

    // Gesuchter Standort der abgeschlossenen Runde.
    val location: GameLocation, // Speichert den Standort, der in diesem Guess gesucht wurde.

    // Gesetzter Pin des Spielers.
    // Die Werte können null sein, wenn im klassischen Modus die Zeit abgelaufen ist.
    val guessedLatitude: Double? = null, // Speichert den Breitengrad des gesetzten Pins.
    val guessedLongitude: Double? = null, // Speichert den Längengrad des gesetzten Pins.
    val distanceKm: Double? = null, // Speichert die Entfernung zwischen Guess und richtigem Standort.

    // Standortwertung.
    val locationPoints: Int, // Speichert die Punkte für die Standortschätzung.

    // Historische Zusatzwertung.
    val guessedYear: Int? = null, // Speichert die Jahreseingabe im historischen Modus.
    val yearPoints: Int = 0, // Speichert die Punkte für die Jahresangabe.

    // Klassische Zusatzwertung.
    val remainingTime: Int? = null, // Speichert die Restzeit im klassischen Modus.
    val timePoints: Int = 0, // Speichert die Punkte für die verbleibende Zeit.

    // Hint Nutzung und möglicher Punktabzug.
    val hintUsed: Boolean = false, // Speichert, ob der Hint in diesem Guess verwendet wurde.
    val hintPenalty: Int = 0, // Speichert den tatsächlichen Punktabzug für den Hint.

    // Finale Rundensumme nach allen Einzelwertungen.
    val totalPoints: Int // Speichert die Gesamtpunktzahl dieses Guesses.
)
