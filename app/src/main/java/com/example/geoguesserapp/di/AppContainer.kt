package com.example.geoguesserapp.di

import android.content.Context
import com.example.geoguesserapp.data.datasource.LocationXmlDataSource
import com.example.geoguesserapp.data.repository.FirebaseAuthRepository
import com.example.geoguesserapp.data.repository.FirebaseLeaderboardRepository
import com.example.geoguesserapp.data.repository.LocalPlayerRepository
import com.example.geoguesserapp.data.repository.LocationRepositoryImpl
import com.example.geoguesserapp.domain.repository.AuthRepository
import com.example.geoguesserapp.domain.repository.LeaderboardRepository
import com.example.geoguesserapp.domain.repository.LocationRepository
import com.example.geoguesserapp.domain.repository.PlayerRepository
import com.example.geoguesserapp.domain.usecase.CalculateDistanceUseCase
import com.example.geoguesserapp.domain.usecase.CalculateLocationPointsUseCase
import com.example.geoguesserapp.domain.usecase.CalculateTimePointsUseCase
import com.example.geoguesserapp.domain.usecase.CalculateTotalPointsUseCase
import com.example.geoguesserapp.domain.usecase.CalculateYearPointsUseCase
import com.example.geoguesserapp.domain.usecase.GetRandomLocationsUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// In dieser Klasse werden die wichtigsten Abhängigkeiten der App zentral erstellt.
// Dadurch müssen die einzelnen Klassen ihre benötigten Objekte nicht selbst erzeugen.
// Die Abhängigkeiten werden später von außen an die jeweiligen Klassen übergeben.
class AppContainer(context: Context) {

    // Erstellt den Zugriff auf die beiden Standort XML Dateien.
    private val locationXmlDataSource = LocationXmlDataSource(context)

    // Stellt Firebase Authentication bereit.
    private val firebaseAuth = FirebaseAuth.getInstance()

    // Stellt den Zugriff auf Cloud Firestore bereit.
    private val firestore = FirebaseFirestore.getInstance()

    // Verbindet die XML DataSource mit dem Standort Repository.
    val locationRepository: LocationRepository = LocationRepositoryImpl(locationXmlDataSource)

    // Stellt die Firebase Auth Logik über unsere manuelle Dependency Injection bereit.
    val authRepository: AuthRepository = FirebaseAuthRepository(firebaseAuth)

    // Verwendet für das Leaderboard Cloud Firestore.
    // Das LeaderboardViewModel arbeitet weiterhin nur mit dem Repository Interface.
    val leaderboardRepository: LeaderboardRepository = FirebaseLeaderboardRepository(
            firestore = firestore,
            authRepository = authRepository
        )

    // Stellt das Repository für den lokal gespeicherten Spielernamen bereit.
    val playerRepository: PlayerRepository = LocalPlayerRepository(context)

    // Erstellt die zufällige Auswahl der 5 Standorte.
    val getRandomLocationsUseCase = GetRandomLocationsUseCase(locationRepository)

    // Erstellt die Berechnung der Entfernung zwischen zwei Koordinaten.
    val calculateDistanceUseCase = CalculateDistanceUseCase()

    // Erstellt die Berechnung der Standortpunkte.
    val calculateLocationPointsUseCase = CalculateLocationPointsUseCase()

    // Erstellt die Zeitpunkte für den klassischen Modus.
    val calculateTimePointsUseCase = CalculateTimePointsUseCase()

    // Erstellt die Jahrespunkte für den historischen Modus.
    val calculateYearPointsUseCase = CalculateYearPointsUseCase()

    // Erstellt die Gesamtpunktzahl eines Guesses.
    val calculateTotalPointsUseCase = CalculateTotalPointsUseCase()
}