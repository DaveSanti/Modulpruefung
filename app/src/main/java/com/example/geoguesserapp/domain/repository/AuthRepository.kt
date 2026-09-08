package com.example.geoguesserapp.domain.repository

// Beschreibt die anonyme Firebase Anmeldung unabhängig von der konkreten Implementierung.
interface AuthRepository {

    // Erstellt eine anonyme Firebase Anmeldung, falls noch kein Nutzer angemeldet ist.
    suspend fun ensureAnonymousSignIn()

    // Gibt die eindeutige Firebase UID des aktuell angemeldeten Nutzers zurück.
    fun getCurrentUserId(): String?
}
