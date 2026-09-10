package com.example.geoguesserapp.presentation.leaderboard

import com.example.geoguesserapp.domain.model.GameMode
import com.example.geoguesserapp.domain.model.LeaderboardEntry

// Speichert den aktuellen Zustand des Leaderboards.
// selectedMode bestimmt, welches der beiden Leaderboards angezeigt wird.
data class LeaderboardUiState(
    // Enthält nur die Einträge des aktuell ausgewählten Spielmodus.
    val entries: List<LeaderboardEntry> = emptyList(),

    // Steuert den Umschalter zwischen klassischem und historischem Leaderboard.
    val selectedMode: GameMode = GameMode.CLASSIC,

    // Solange der erste Firebase Stand noch nicht geladen ist, kann die UI einen Ladezustand zeigen.
    val isLoading: Boolean = true
)
