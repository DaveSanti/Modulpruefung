package com.example.geoguesserapp.domain.usecase

import com.example.geoguesserapp.util.Constants

// Dieser Use Case setzt die einzelnen Punkte eines Guesses zusammen.
// Standortpunkte, Zeitpunkte und Jahrespunkte werden getrennt berechnet.
// Erst hier werden sie zu einer Gesamtpunktzahl zusammengefügt.
// Dadurch bleiben die verschiedenen Bewertungsregeln klar voneinander getrennt.

class CalculateTotalPointsUseCase {

    operator fun invoke(
        locationPoints: Int,
        timePoints: Int = 0,
        yearPoints: Int = 0,
        hintUsed: Boolean = false
    ): Int {

        var totalPoints = locationPoints + timePoints + yearPoints

        // Wurde ein Hint verwendet, werden die festgelegten Hint Punkte abgezogen.
        if (hintUsed) {
            totalPoints -= Constants.HINT_PENALTY
        }

        // Eine Runde kann niemals weniger als 0 oder mehr als 5000 Punkte ergeben.
        return totalPoints.coerceIn(
            0,
            Constants.MAX_POINTS_PER_ROUND
        )
    }
}