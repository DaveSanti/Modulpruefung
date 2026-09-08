package com.example.geoguesserapp.domain.usecase

import kotlin.math.*

// Dieser Use Case berechnet die Entfernung zwischen dem richtigen Standort und dem vom Spieler gesetzten Pin.
// Dafür wird die Haversine Formel verwendet.
// Sie berücksichtigt die Krümmung der Erde und eignet sich deshalb
// für die Berechnung von Entfernungen zwischen zwei Koordinaten.

class CalculateDistanceUseCase {

    operator fun invoke(
        realLatitude: Double,
        realLongitude: Double,
        guessedLatitude: Double,
        guessedLongitude: Double
    ): Double {

        val earthRadius = 6371.0 // Durchschnittlicher Radius der Erde in Kilometern.

        // Die Koordinaten werden von Grad in Radiant umgerechnet, da die mathematischen Funktionen mit Radiant arbeiten.

        val latitudeDifference = Math.toRadians(guessedLatitude - realLatitude)
        val longitudeDifference = Math.toRadians(guessedLongitude - realLongitude)

        val realLatitudeRadians = Math.toRadians(realLatitude)
        val guessedLatitudeRadians = Math.toRadians(guessedLatitude)

        // Dieser Teil ist die eigentliche Formel.

        val a =
            sin(latitudeDifference / 2).pow(2) +
                    cos(realLatitudeRadians) *
                    cos(guessedLatitudeRadians) *
                    sin(longitudeDifference / 2).pow(2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c // Gibt die Entfernung zwischen beiden Punkten in Kilometern zurück.
    }
}