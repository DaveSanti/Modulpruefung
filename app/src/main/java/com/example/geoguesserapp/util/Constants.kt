package com.example.geoguesserapp.util

// Hier werden feste Werte gespeichert, die an mehreren Stellen in der App benötigt werden.
// Dadurch müssen wir die Werte später nur an einer Stelle ändern.

object Constants {

    const val ROUNDS_PER_GAME = 5 // Legt fest, wie viele Guesses ein Spiel besitzt.
    const val CLASSIC_TIME_SECONDS = 60 // Legt die maximale Zeit pro Guess im klassischen Modus fest.
    const val CLASSIC_LOCATION_MAX_POINTS = 4000 // Maximale Standortpunkte im klassischen Modus.
    const val CLASSIC_TIME_MAX_POINTS = 1000 // Maximale Zeitpunkte im klassischen Modus.
    const val HISTORICAL_LOCATION_MAX_POINTS = 3500 // Maximale Standortpunkte im historischen Modus.
    const val HISTORICAL_YEAR_MAX_POINTS = 1500 // Maximale Punkte für die Jahresangabe.
    const val HINT_PENALTY = 500 // Punkte, die bei Verwendung eines Hints abgezogen werden.
    const val MAX_POINTS_PER_ROUND = 5000 // Maximale Gesamtpunktzahl pro Guess.
    const val MAX_POINTS_PER_GAME = 25000 // Maximale Gesamtpunktzahl bei 5 Guesses.
    const val MAP_STYLE_URL = "https://tiles.openfreemap.org/styles/liberty"
}