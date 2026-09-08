package com.example.geoguesserapp.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.geoguesserapp.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Erstellt einen eigenen DataStore für die lokalen Spielerdaten.
private val Context.playerDataStore by preferencesDataStore(
    name = "player"
)

// Lokale Implementierung des PlayerRepository.
// Der Spielername wird dauerhaft mit DataStore auf dem Gerät gespeichert.
class LocalPlayerRepository(
    private val context: Context
) : PlayerRepository {

    companion object {

        // Unter diesem Schlüssel wird der Spielername gespeichert.
        private val PLAYER_NAME_KEY =
            stringPreferencesKey("player_name")
    }

    // Beobachtet den gespeicherten Spielernamen.
    // Wenn noch kein Name gespeichert wurde, wird null zurückgegeben.
    override fun observePlayerName(): Flow<String?> {
        return context.playerDataStore.data.map { preferences ->
            preferences[PLAYER_NAME_KEY]
        }
    }

    // Speichert den Spielernamen dauerhaft im DataStore.
    override suspend fun savePlayerName(
        playerName: String
    ) {
        val cleanPlayerName = playerName.trim()

        // Leere Namen werden nicht gespeichert.
        if (cleanPlayerName.isBlank()) return

        context.playerDataStore.edit { preferences ->
            preferences[PLAYER_NAME_KEY] = cleanPlayerName
        }
    }
}