package com.example.geoguesserapp.data.repository

import com.example.geoguesserapp.domain.model.GameMode
import com.example.geoguesserapp.domain.model.LeaderboardEntry
import com.example.geoguesserapp.domain.repository.AuthRepository
import com.example.geoguesserapp.domain.repository.LeaderboardRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.security.MessageDigest

// Firebase Implementierung des LeaderboardRepository.
// Die Highscores werden online in Cloud Firestore gespeichert.
// Der playerTag macht gleiche Spielernamen auf verschiedenen Geräten unterscheidbar.
class FirebaseLeaderboardRepository(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
) : LeaderboardRepository {

    companion object {
        private const val COLLECTION_LEADERBOARD = "leaderboard"
    }

    // Beobachtet alle Highscores in Firestore.
    // Änderungen werden automatisch über den Flow an das ViewModel weitergegeben.
    override fun observeLeaderboard(): Flow<List<LeaderboardEntry>> = callbackFlow {

        val listener = firestore
            .collection(COLLECTION_LEADERBOARD)
            .orderBy("bestScore", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    // Bei einem Firestore Fehler wird der Flow geschlossen.
                    // Das ViewModel erhält dadurch kein teilweise falsches Leaderboard.
                    close(error)
                    return@addSnapshotListener
                }

                // Unvollständige oder unerwartete Dokumente werden übersprungen.
                // So bleibt die Rangliste stabil, auch wenn einzelne Firebase Einträge fehlerhaft sind.
                val entries = snapshot?.documents?.mapNotNull { document ->

                    val playerName = document.getString("playerName")
                        ?: return@mapNotNull null

                    val normalizedName = document.getString("normalizedName")
                        ?: return@mapNotNull null

                    val playerTag = document.getString("playerTag")
                        ?: return@mapNotNull null

                    val bestScore = document.getLong("bestScore")
                        ?.toInt()
                        ?: return@mapNotNull null

                    val gameModeString = document.getString("gameMode")
                        ?: return@mapNotNull null

                    val gameMode = runCatching {
                        GameMode.valueOf(gameModeString)
                    }.getOrNull() ?: return@mapNotNull null

                    LeaderboardEntry(
                        playerName = playerName,
                        normalizedName = normalizedName,
                        playerTag = playerTag,
                        bestScore = bestScore,
                        gameMode = gameMode
                    )
                } ?: emptyList()

                trySend(entries)
            }

        // Entfernt den Firestore Listener wieder, wenn der Flow nicht mehr benötigt wird.
        awaitClose {
            listener.remove()
        }
    }

    // Speichert den Highscore eines Spielernamens für einen bestimmten Spielmodus.
    // Gleicher Firebase Nutzer, gleicher Name und gleicher Modus
    // führen immer wieder zum selben Firestore Dokument.
    override suspend fun saveScore(
        playerName: String,
        gameMode: GameMode,
        score: Int
    ): Boolean {
        val userId = authRepository.getCurrentUserId() ?: return false
        val cleanPlayerName = playerName.trim()

        if (cleanPlayerName.isBlank()) return false

        // Normalisiert den Namen für einen einheitlichen Vergleich.
        val normalizedName = cleanPlayerName.lowercase()

        // Erstellt eine kurze sichtbare Kennung aus der Firebase UID.
        val playerTag = createPlayerTag(userId)

        // Erstellt einen stabilen Hash aus dem Spielernamen.
        val nameHash = createNameHash(normalizedName)

        // Kombination aus Firebase UID, Name und Spielmodus.
        // Dadurch bleibt derselbe Spieler auf demselben Gerät eindeutig.
        val documentId =
            "${userId}_${nameHash}_${gameMode.name}"

        val documentReference = firestore
            .collection(COLLECTION_LEADERBOARD)
            .document(documentId)

        val currentDocument =
            documentReference.get().await()

        val currentScore =
            currentDocument.getLong("bestScore")?.toInt()

        // Speichert nur dann,
        // wenn noch kein Eintrag existiert oder der neue Score höher ist.
        if (currentScore == null || score > currentScore) {

            val data = mapOf(
                "userId" to userId,
                "playerName" to cleanPlayerName,
                "normalizedName" to normalizedName,
                "playerTag" to playerTag,
                "gameMode" to gameMode.name,
                "bestScore" to score
            )

            documentReference
                .set(data)
                .await()

            return true
        }

        return false
    }

    // Erstellt aus der Firebase UID eine vierstellige sichtbare Kennung.
    // Dieselbe UID erhält bei jedem Start wieder denselben Tag.
    private fun createPlayerTag(
        userId: String
    ): String {
        val number =
            userId.hashCode().toUInt() % 10000u

        return number
            .toString()
            .padStart(4, '0')
    }

    // Erstellt aus dem normalisierten Spielernamen einen stabilen Hash.
    // Dadurch kann der Name sicher Bestandteil der Dokument ID werden.
    private fun createNameHash(
        normalizedName: String
    ): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(normalizedName.toByteArray())

        return bytes.joinToString("") {
            "%02x".format(it)
        }.take(16)
    }
}
