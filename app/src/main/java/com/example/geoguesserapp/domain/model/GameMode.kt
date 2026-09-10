package com.example.geoguesserapp.domain.model

// Über GameMode wird festgelegt, welcher Spielmodus gerade verwendet wird.
// Dadurch können beide Modi dieselbe Grundstruktur der App nutzen.
enum class GameMode {
    // Klassischer Geoguessr Modus mit Standort- und Zeitpunkten.
    CLASSIC,

    // Historischer Modus mit Standort- und Jahrespunkten.
    HISTORICAL
}
