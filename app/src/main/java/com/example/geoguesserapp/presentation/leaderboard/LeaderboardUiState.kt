package com.example.geoguesserapp.presentation.leaderboard

import com.example.geoguesserapp.domain.model.GameMode
import com.example.geoguesserapp.domain.model.LeaderboardEntry

// Speichert den aktuellen Zustand des Leaderboards.
// selectedMode bestimmt, welches der beiden Leaderboards angezeigt wird.
data class LeaderboardUiState(
    val entries: List<LeaderboardEntry> = emptyList(),
    val selectedMode: GameMode = GameMode.CLASSIC,
    val isLoading: Boolean = true
)