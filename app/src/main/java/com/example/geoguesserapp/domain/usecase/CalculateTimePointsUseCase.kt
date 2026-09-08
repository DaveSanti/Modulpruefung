package com.example.geoguesserapp.domain.usecase

import com.example.geoguesserapp.util.Constants

// Dieser Use Case berechnet den Zeitbonus im klassischen Modus.
// Je mehr Zeit beim Abschicken des Guesses übrig ist, desto mehr zusätzliche Punkte bekommt der Spieler.
// Der historische Modus verwendet diesen Use Case nicht, da dort bewusst keine Zeitbegrenzung vorgesehen ist.

class CalculateTimePointsUseCase {

    operator fun invoke(remainingTime: Int): Int {

        // Stellt sicher, dass die Zeit nur zwischen 0 und 60 Sekunden liegen kann.
        val validTime = remainingTime.coerceIn(
            0,
            Constants.CLASSIC_TIME_SECONDS
        )

        // Berechnet den prozentualen Anteil der verbleibenden Zeit.
        val timeFactor = validTime.toDouble() / Constants.CLASSIC_TIME_SECONDS

        // Der Zeitfaktor wird mit den maximal möglichen Zeitpunkten multipliziert.
        return (timeFactor * Constants.CLASSIC_TIME_MAX_POINTS).toInt()
    }
}