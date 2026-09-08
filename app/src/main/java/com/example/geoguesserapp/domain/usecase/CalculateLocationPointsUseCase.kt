package com.example.geoguesserapp.domain.usecase

import kotlin.math.exp
import kotlin.math.roundToInt

// Dieser Use Case berechnet die Standortpunkte anhand der Entfernung.
// Die Berechnung liegt bewusst in einer eigenen Klasse.
// Dadurch kann die Punkteformel später verändert werden,
// ohne dass das ViewModel oder andere Teile der App angepasst werden müssen.
class CalculateLocationPointsUseCase {

    operator fun invoke(
        distanceKm: Double,
        maxPoints: Int
    ): Int {

        // Bis einschließlich 5 km Entfernung gibt es die volle Standortpunktzahl.
        // Dadurch wird ein Guess innerhalb derselben Stadt nicht unnötig streng bewertet.
        val toleranceKm = 5.0

        // Dieser Faktor bestimmt, wie schnell die Punkte mit zunehmender Entfernung sinken.
        // Der Wert wurde so gewählt, dass bei ungefähr 461 km Entfernung
        // noch etwa zwei Drittel der maximalen Standortpunkte erreicht werden.
        // Die 461 km orientieren sich an dem von uns verwendeten
        // durchschnittlichen Radius eines Landes.
        val distanceFactor = 1170.0

        // Innerhalb der Toleranzzone werden keine Punkte abgezogen.
        if (distanceKm <= toleranceKm) return maxPoints

        // Die ersten 5 km werden von der tatsächlichen Entfernung abgezogen.
        // Dadurch beginnt die Punktekurve erst außerhalb der Toleranzzone.
        val adjustedDistance = distanceKm - toleranceKm

        // Die Punkte sinken ab 5 km kontinuierlich mit steigender Entfernung.
        // Dadurch entstehen keine festen Punktestufen.
        val points = maxPoints * exp(-adjustedDistance / distanceFactor)

        return points
            .roundToInt()
            .coerceIn(0, maxPoints) // Verhindert Werte unter 0 oder über der maximalen Punktzahl.
    }
}