package com.example.geoguesserapp.domain.usecase

import com.example.geoguesserapp.util.Constants
import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.roundToInt

// Dieser Use Case berechnet im historischen Modus die Punkte für die Jahresangabe.
// Entscheidend ist die Differenz zwischen dem richtigen Jahr und dem vom Spieler eingegebenen Jahr.
// Die Berechnung befindet sich bewusst an einer zentralen Stelle,
// damit wir die Schwierigkeit später noch einfach anpassen können.

class CalculateYearPointsUseCase {

    operator fun invoke(
        realYear: Int,
        guessedYear: Int
    ): Int {

        // Berechnet die absolute Differenz zwischen beiden Jahreszahlen.
        // Dadurch spielt es keine Rolle, ob der Spieler zu früh oder zu spät geschätzt hat.
        val yearDifference = abs(realYear - guessedYear)

        // Dieser Faktor bestimmt, wie schnell die Punkte bei einer größeren
        // Abweichung vom richtigen Jahr sinken.
        // Diesen Wert werden wir später noch anhand echter Spieltests anpassen.
        val yearFactor = 350.0

        val points = Constants.HISTORICAL_YEAR_MAX_POINTS * exp(-yearDifference / yearFactor)

        return points
            .roundToInt()
            .coerceIn(0, Constants.HISTORICAL_YEAR_MAX_POINTS)
    }
}