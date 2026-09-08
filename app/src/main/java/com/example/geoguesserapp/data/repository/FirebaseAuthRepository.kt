package com.example.geoguesserapp.data.repository

import com.example.geoguesserapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

// Firebase Implementierung des AuthRepository.
// Die Anmeldung erfolgt vollständig im Hintergrund und benötigt keinen Login Screen.
class FirebaseAuthRepository(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    // Prüft, ob bereits ein anonymer Firebase Nutzer vorhanden ist.
    // Falls nicht, wird automatisch ein neuer anonymer Nutzer erstellt.
    override suspend fun ensureAnonymousSignIn() {
        if (firebaseAuth.currentUser != null) return

        firebaseAuth
            .signInAnonymously()
            .await()
    }

    // Liefert die eindeutige Firebase UID des aktuellen Nutzers.
    override fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }
}