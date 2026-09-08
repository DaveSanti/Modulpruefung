package com.example.geoguesserapp.presentation.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoguesserapp.domain.model.GameMode
import com.example.geoguesserapp.domain.model.LeaderboardEntry
import com.example.geoguesserapp.domain.repository.LeaderboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Verbindet das LeaderboardRepository mit der Benutzeroberfläche.
// Das ViewModel kennt nicht die konkrete Art der Speicherung.
class LeaderboardViewModel(
    private val leaderboardRepository: LeaderboardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LeaderboardUiState())
    val uiState: StateFlow<LeaderboardUiState> = _uiState.asStateFlow()

    // Speichert intern alle Highscores beider Modi.
    // Für die Oberfläche wird anschließend nur der ausgewählte Modus angezeigt.
    private var allEntries: List<LeaderboardEntry> = emptyList()

    init {
        observeLeaderboard()
    }

    // Beobachtet dauerhaft alle gespeicherten Highscores.
    // Das Repository liefert die Online Daten; das ViewModel speichert sie nur temporär
    // und filtert anschließend nach dem aktuell ausgewählten Spielmodus.
    private fun observeLeaderboard() {
        viewModelScope.launch {
            leaderboardRepository.observeLeaderboard().collect { entries ->
                allEntries = entries
                updateVisibleEntries()
            }
        }
    }

    // Wechselt zwischen klassischem und historischem Leaderboard.
    fun selectMode(gameMode: GameMode) {
        _uiState.value = _uiState.value.copy(
            selectedMode = gameMode
        )

        updateVisibleEntries()
    }

    // Filtert die Einträge nach dem aktuell ausgewählten Modus
    // und sortiert sie anschließend nach dem höchsten Score.
    private fun updateVisibleEntries() {
        val selectedMode = _uiState.value.selectedMode

        _uiState.value = _uiState.value.copy(
            entries = allEntries
                .filter { it.gameMode == selectedMode }
                .sortedByDescending { it.bestScore },
            isLoading = false
        )
    }

    // Speichert einen Score über das Repository.
    suspend fun saveScore(
        playerName: String,
        gameMode: GameMode,
        score: Int
    ): Boolean {
        return leaderboardRepository.saveScore(
            playerName = playerName,
            gameMode = gameMode,
            score = score
        )
    }
}
