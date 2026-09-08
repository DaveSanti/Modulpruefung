package com.example.geoguesserapp.domain.repository

import kotlinx.coroutines.flow.Flow

// Beschreibt den Zugriff auf den lokal gespeicherten Spielernamen.
// Dadurch bleibt die konkrete Speichertechnik von der restlichen App getrennt.
interface PlayerRepository {

    fun observePlayerName(): Flow<String?>  // Beobachtet den gespeicherten Spielernamen.
    suspend fun savePlayerName(playerName: String) // Speichert einen neuen Spielernamen dauerhaft auf dem Gerät.
}