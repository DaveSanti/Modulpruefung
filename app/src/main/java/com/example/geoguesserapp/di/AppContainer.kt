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
    // Diese DataSource kennt die Android Resources und liest daraus die Standortlisten.
    private val locationXmlDataSource = LocationXmlDataSource(context)

    // Stellt Firebase Authentication bereit.
    // Darüber bekommt jedes Gerät beziehungsweise jeder anonyme Nutzer eine eigene UID.
    private val firebaseAuth = FirebaseAuth.getInstance()

    // Stellt den Zugriff auf Cloud Firestore bereit.
    // Firestore ist die einzige Datenquelle für das Leaderboard.
    private val firestore = FirebaseFirestore.getInstance()

    // Verbindet die XML DataSource mit dem Standort Repository.
    // Die Domain Schicht arbeitet dadurch nur mit dem Interface.
    val locationRepository: LocationRepository = LocationRepositoryImpl(locationXmlDataSource)

    // Stellt die Firebase Auth Logik über unsere manuelle Dependency Injection bereit.
    // Die AuthRepository Schnittstelle versteckt die konkrete Firebase Klasse.
    val authRepository: AuthRepository = FirebaseAuthRepository(firebaseAuth)

    // Verwendet für das Leaderboard Cloud Firestore.
    // Das LeaderboardViewModel arbeitet weiterhin nur mit dem Repository Interface.
    val leaderboardRepository: LeaderboardRepository = FirebaseLeaderboardRepository(
            firestore = firestore,
            authRepository = authRepository
        )

    // Stellt das Repository für den lokal gespeicherten Spielernamen bereit.
    // Diese lokale Speicherung betrifft nur den Namen und nicht mehr das Leaderboard.
    val playerRepository: PlayerRepository = LocalPlayerRepository(context)

    // Erstellt die zufällige Auswahl der 5 Standorte.
    // Der Use Case kapselt, welche Liste für welchen Modus verwendet wird.
    val getRandomLocationsUseCase = GetRandomLocationsUseCase(locationRepository)

    // Erstellt die Berechnung der Entfernung zwischen zwei Koordinaten.
    // Diese Berechnung wird für beide Spielmodi benötigt.
    val calculateDistanceUseCase = CalculateDistanceUseCase()

    // Erstellt die Berechnung der Standortpunkte.
    // Die konkreten Maximalpunkte unterscheiden sich je nach Spielmodus.
    val calculateLocationPointsUseCase = CalculateLocationPointsUseCase()

    // Erstellt die Zeitpunkte für den klassischen Modus.
    // Im historischen Modus gibt es keine Zeitwertung.
    val calculateTimePointsUseCase = CalculateTimePointsUseCase()

    // Erstellt die Jahrespunkte für den historischen Modus.
    // Im klassischen Modus wird dieser Use Case nicht verwendet.
    val calculateYearPointsUseCase = CalculateYearPointsUseCase()

    // Erstellt die Gesamtpunktzahl eines Guesses.
    // Hier werden Standortpunkte, Zeitpunkte, Jahrespunkte und Hint Abzug zusammengeführt.
    val calculateTotalPointsUseCase = CalculateTotalPointsUseCase()
}
