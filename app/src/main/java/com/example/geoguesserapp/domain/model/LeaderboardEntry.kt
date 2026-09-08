package com.example.geoguesserapp.domain.model

// Repräsentiert einen einzelnen Eintrag im Leaderboard.
// playerName ist der sichtbare Originalname.
// normalizedName wird nur intern für Vergleiche ohne Groß- und Kleinschreibung verwendet.
data class LeaderboardEntry(
    val playerName: String,
    val normalizedName: String,
    val playerTag: String,
    val bestScore: Int,
    val gameMode: GameMode
)
