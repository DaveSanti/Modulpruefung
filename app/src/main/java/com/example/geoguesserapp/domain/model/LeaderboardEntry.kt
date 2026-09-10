package com.example.geoguesserapp.domain.model

// Repräsentiert einen einzelnen Eintrag im Leaderboard.
// playerName ist der sichtbare Originalname.
// normalizedName wird nur intern für Vergleiche ohne Groß- und Kleinschreibung verwendet.
data class LeaderboardEntry(
    // Sichtbarer Name, der im Leaderboard angezeigt wird.
    val playerName: String,

    // Vereinheitlichter Name für Vergleiche und Firestore Dokument IDs.
    val normalizedName: String,

    // Kurze Kennung der Firebase UID, damit gleiche Namen unterscheidbar bleiben.
    val playerTag: String,

    // Höchster gespeicherter Score dieses Spielers in diesem Modus.
    val bestScore: Int,

    // Gibt an, ob der Eintrag zum klassischen oder historischen Leaderboard gehört.
    val gameMode: GameMode
)
