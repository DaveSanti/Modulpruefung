package com.example.geoguesserapp.domain.repository

import com.example.geoguesserapp.domain.model.GameMode
import com.example.geoguesserapp.domain.model.LeaderboardEntry
import kotlinx.coroutines.flow.Flow

// Beschreibt alle Funktionen, die eine Leaderboard Datenquelle bereitstellen muss.
// Die Presentation Schicht kennt dadurch nicht die konkrete Speicherung.
// Die konkrete Speicherung erfolgt aktuell online über Firebase.
interface LeaderboardRepository {

// Beobachtet das Leaderboard dauerhaft.
// Sobald sich die gespeicherten Daten ändern, liefert der Flow automatisch eine neue Liste.
fun observeLeaderboard(): Flow<List<LeaderboardEntry>>

// Speichert einen Highscore für den angegebenen Spieler und Spielmodus.
// Der Rückgabewert zeigt an, ob ein neuer persönlicher Bestwert gespeichert wurde.
suspend fun saveScore(
    playerName: String,
    gameMode: GameMode,
    score: Int
): Boolean
}
