package com.example.geoguesserapp.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoguesserapp.domain.model.GameLocation
import com.example.geoguesserapp.domain.model.GameMode
import com.example.geoguesserapp.domain.model.GuessResult
import com.example.geoguesserapp.domain.model.HistoricalLocation
import com.example.geoguesserapp.domain.repository.PlayerRepository
import com.example.geoguesserapp.domain.usecase.CalculateDistanceUseCase
import com.example.geoguesserapp.domain.usecase.CalculateLocationPointsUseCase
import com.example.geoguesserapp.domain.usecase.CalculateTimePointsUseCase
import com.example.geoguesserapp.domain.usecase.CalculateTotalPointsUseCase
import com.example.geoguesserapp.domain.usecase.CalculateYearPointsUseCase
import com.example.geoguesserapp.domain.usecase.GetRandomLocationsUseCase
import com.example.geoguesserapp.domain.repository.AuthRepository
import com.example.geoguesserapp.util.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Das GameViewModel steuert den kompletten Ablauf eines laufenden Spiels.
// Die UI beobachtet den StateFlow und reagiert automatisch auf Änderungen im GameUiState.
class GameViewModel(
    private val getRandomLocationsUseCase: GetRandomLocationsUseCase,
    private val calculateDistanceUseCase: CalculateDistanceUseCase,
    private val calculateLocationPointsUseCase: CalculateLocationPointsUseCase,
    private val calculateTimePointsUseCase: CalculateTimePointsUseCase,
    private val calculateYearPointsUseCase: CalculateYearPointsUseCase,
    private val calculateTotalPointsUseCase: CalculateTotalPointsUseCase,
    private val playerRepository: PlayerRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState()) // Speichert den veränderbaren Spielzustand.
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow() // Stellt den Spielzustand für die UI bereit.

    private var timerJob: Job? = null // Speichert die aktuell laufende Timer Coroutine.

    init {
        observePlayerName()
        signInAnonymously()
    }

    // Beobachtet den dauerhaft gespeicherten Spielernamen.
    // Sobald DataStore den Namen geladen hat, wird der UI State aktualisiert.
    private fun observePlayerName() {
        viewModelScope.launch {
            playerRepository.observePlayerName().collect { playerName ->
                _uiState.value = _uiState.value.copy(
                    playerName = playerName ?: "",
                    isPlayerLoaded = true
                )
            }
        }
    }

    // Stellt beim Start der App sicher, dass ein anonymer Firebase Nutzer existiert.
    // Für den Spieler passiert die Anmeldung vollständig im Hintergrund.
    private fun signInAnonymously() {
        viewModelScope.launch {
            try {
                authRepository.ensureAnonymousSignIn()
            } catch (exception: Exception) {
                // Firebase Fehler verhindern nicht die lokale Nutzung der App.
            }
        }
    }

    // Speichert den Spielernamen im UI State und zusätzlich dauerhaft im lokalen DataStore.
    fun setPlayerName(playerName: String) {
        val cleanPlayerName = playerName.trim()

        if (cleanPlayerName.isBlank()) return

        _uiState.value = _uiState.value.copy(
            playerName = cleanPlayerName
        )

        viewModelScope.launch {
            playerRepository.savePlayerName(cleanPlayerName)
        }
    }

    // Speichert den ausgewählten Spielmodus.
    fun setGameMode(mode: GameMode) {
        _uiState.value = _uiState.value.copy(
            gameMode = mode
        )
    }

    // Startet ein neues Spiel mit 5 zufälligen Standorten.
    // Alle Werte eines vorherigen Spiels werden dabei zurückgesetzt.
    fun startGame() {
        val mode = _uiState.value.gameMode ?: return

        _uiState.value = _uiState.value.copy(
            locations = getRandomLocationsUseCase(mode),
            currentRound = 0,
            selectedLatitude = null,
            selectedLongitude = null,
            guessedYear = null,
            hintUsed = false,
            remainingTime = Constants.CLASSIC_TIME_SECONDS,
            results = emptyList(),
            totalScore = 0,
            showRoundResult = false,
            isGameFinished = false
        )

        startTimer()
    }

    // Speichert den auf der Karte ausgewählten Standort.
    fun selectLocation(
        latitude: Double,
        longitude: Double
    ) {
        _uiState.value = _uiState.value.copy(
            selectedLatitude = latitude,
            selectedLongitude = longitude
        )
    }

    // Speichert die eingegebene Jahreszahl im historischen Modus.
    fun setGuessedYear(year: Int) {
        _uiState.value = _uiState.value.copy(
            guessedYear = year
        )
    }

    // Markiert den Hint für den aktuellen Guess als verwendet.
    fun useHint() {
        if (_uiState.value.hintUsed) return

        _uiState.value = _uiState.value.copy(
            hintUsed = true
        )
    }

    // Wertet den aktuellen Guess aus.
    // Dabei werden Entfernung, Punkte und das Ergebnis der aktuellen Runde berechnet.
    fun submitGuess() {
        stopTimer()

        val state = _uiState.value
        val location = state.locations.getOrNull(state.currentRound) ?: return
        val guessedLatitude = state.selectedLatitude ?: return
        val guessedLongitude = state.selectedLongitude ?: return

        // Im historischen Modus muss zusätzlich eine Jahreszahl vorhanden sein.
        if (
            state.gameMode == GameMode.HISTORICAL &&
            state.guessedYear == null
        ) {
            return
        }

        val distanceKm = calculateDistanceUseCase(
            realLatitude = location.latitude,
            realLongitude = location.longitude,
            guessedLatitude = guessedLatitude,
            guessedLongitude = guessedLongitude
        )

        val locationPoints = calculateLocationPoints(
            distanceKm = distanceKm,
            mode = state.gameMode
        )

        val timePoints = calculateTimePoints(state)
        val yearPoints = calculateYearPoints(
            state = state,
            location = location
        )

        val totalPoints = calculateTotalPointsUseCase(
            locationPoints = locationPoints,
            timePoints = timePoints,
            yearPoints = yearPoints,
            hintUsed = state.hintUsed
        )

        // Speichert alle Daten des aktuellen Guesses für die spätere Ergebnisanzeige.
        val result = GuessResult(
            location = location,
            guessedLatitude = guessedLatitude,
            guessedLongitude = guessedLongitude,
            distanceKm = distanceKm,
            locationPoints = locationPoints,
            guessedYear = state.guessedYear,
            yearPoints = yearPoints,
            remainingTime = if (state.gameMode == GameMode.CLASSIC) state.remainingTime else null,
            timePoints = timePoints,
            hintUsed = state.hintUsed,
            hintPenalty = if (state.hintUsed) Constants.HINT_PENALTY else 0,
            totalPoints = totalPoints
        )

        finishRound(result)
    }

    // Berechnet die Standortpunkte abhängig vom ausgewählten Spielmodus.
    private fun calculateLocationPoints(
        distanceKm: Double,
        mode: GameMode?
    ): Int {
        val maxPoints = when (mode) {
            GameMode.CLASSIC -> Constants.CLASSIC_LOCATION_MAX_POINTS
            GameMode.HISTORICAL -> Constants.HISTORICAL_LOCATION_MAX_POINTS
            null -> return 0
        }

        return calculateLocationPointsUseCase(
            distanceKm = distanceKm,
            maxPoints = maxPoints
        )
    }

    // Berechnet die Zeitpunkte ausschließlich im klassischen Modus.
    private fun calculateTimePoints(
        state: GameUiState
    ): Int {
        return if (state.gameMode == GameMode.CLASSIC) {
            calculateTimePointsUseCase(state.remainingTime)
        } else {
            0
        }
    }

    // Berechnet die Jahrespunkte ausschließlich im historischen Modus.
    private fun calculateYearPoints(
        state: GameUiState,
        location: GameLocation
    ): Int {
        if (
            state.gameMode != GameMode.HISTORICAL ||
            location !is HistoricalLocation
        ) {
            return 0
        }

        val guessedYear = state.guessedYear ?: return 0

        return calculateYearPointsUseCase(
            realYear = location.year,
            guessedYear = guessedYear
        )
    }

    // Speichert das Ergebnis des aktuellen Guesses.
    // Nach jedem Guess wird zuerst der RoundResultScreen angezeigt.
    private fun finishRound(
        result: GuessResult
    ) {
        val state = _uiState.value
        val updatedResults = state.results + result
        val updatedTotalScore = state.totalScore + result.totalPoints
        val gameFinished =
            state.currentRound >= Constants.ROUNDS_PER_GAME - 1

        _uiState.value = state.copy(
            results = updatedResults,
            totalScore = updatedTotalScore,
            showRoundResult = true,
            isGameFinished = gameFinished
        )
    }

    // Wird aufgerufen, wenn der Spieler den RoundResultScreen bestätigt.
    // Nach Runde 5 wird das Spiel beendet, ansonsten startet die nächste Runde.
    fun continueAfterRound() {
        val state = _uiState.value

        if (state.isGameFinished) {
            _uiState.value = state.copy(
                showRoundResult = false
            )
        } else {
            startNextRound(
                updatedResults = state.results,
                updatedTotalScore = state.totalScore
            )
        }
    }

    // Setzt die Werte des vorherigen Guesses zurück und startet die nächste Runde.
    private fun startNextRound(
        updatedResults: List<GuessResult>,
        updatedTotalScore: Int
    ) {
        val state = _uiState.value

        _uiState.value = state.copy(
            currentRound = state.currentRound + 1,
            selectedLatitude = null,
            selectedLongitude = null,
            guessedYear = null,
            hintUsed = false,
            remainingTime = Constants.CLASSIC_TIME_SECONDS,
            results = updatedResults,
            totalScore = updatedTotalScore,
            showRoundResult = false
        )

        startTimer()
    }

    // Startet den 60 Sekunden Timer ausschließlich im klassischen Modus.
    private fun startTimer() {
        if (_uiState.value.gameMode != GameMode.CLASSIC) return

        stopTimer()

        timerJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                remainingTime = Constants.CLASSIC_TIME_SECONDS
            )

            while (_uiState.value.remainingTime > 0) {
                delay(1000)

                _uiState.value = _uiState.value.copy(
                    remainingTime = _uiState.value.remainingTime - 1
                )
            }

            handleTimeExpired()
        }
    }

    // Stoppt die aktuell laufende Timer Coroutine.
    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    // Prüft nach Ablauf des Timers, ob bereits ein Pin gesetzt wurde.
    // Wenn ein Pin vorhanden ist, wird dieser automatisch als Guess verwendet.
    // Ohne gesetzten Pin wird ein Guess mit 0 Punkten gespeichert.
    private fun handleTimeExpired() {
        val state = _uiState.value

        val pinWasSet =
            state.selectedLatitude != null &&
                    state.selectedLongitude != null

        if (pinWasSet) {
            submitGuess()
        } else {
            submitEmptyGuess()
        }
    }

    // Speichert einen Guess mit 0 Punkten, wenn innerhalb der Zeit kein Pin gesetzt wurde.
    // Die Guess Koordinaten und die Distanz bleiben null, weil tatsächlich kein Standort ausgewählt wurde.
    private fun submitEmptyGuess() {
        stopTimer()

        val state = _uiState.value
        val location =
            state.locations.getOrNull(state.currentRound) ?: return

        val result = GuessResult(
            location = location,
            guessedLatitude = null,
            guessedLongitude = null,
            distanceKm = null,
            locationPoints = 0,
            guessedYear = null,
            yearPoints = 0,
            remainingTime = 0,
            timePoints = 0,
            hintUsed = state.hintUsed,
            hintPenalty = 0,
            totalPoints = 0
        )

        finishRound(result)
    }

    // Beendet den Timer endgültig, sobald das ViewModel nicht mehr benötigt wird.
    override fun onCleared() {
        stopTimer()
    }
}