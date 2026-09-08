package com.example.geoguesserapp.domain.model

// Diese Datenklasse speichert das Ergebnis eines einzelnen Guesses.
// Die Ergebnisse werden während des Spiels gesammelt und erst nach dem fünften Guess angezeigt.
data class GuessResult(

    val location: GameLocation, // Speichert den Standort, der in diesem Guess gesucht wurde.
    val guessedLatitude: Double? = null, // Speichert den Breitengrad des gesetzten Pins.
    val guessedLongitude: Double? = null, // Speichert den Längengrad des gesetzten Pins.
    val distanceKm: Double? = null, // Speichert die Entfernung zwischen Guess und richtigem Standort.
    val locationPoints: Int, // Speichert die Punkte für die Standortschätzung.
    val guessedYear: Int? = null, // Speichert die Jahreseingabe im historischen Modus.
    val yearPoints: Int = 0, // Speichert die Punkte für die Jahresangabe.
    val remainingTime: Int? = null, // Speichert die Restzeit im klassischen Modus.
    val timePoints: Int = 0, // Speichert die Punkte für die verbleibende Zeit.
    val hintUsed: Boolean = false, // Speichert, ob der Hint in diesem Guess verwendet wurde.
    val hintPenalty: Int = 0, // Speichert den tatsächlichen Punktabzug für den Hint.
    val totalPoints: Int // Speichert die Gesamtpunktzahl dieses Guesses.
)