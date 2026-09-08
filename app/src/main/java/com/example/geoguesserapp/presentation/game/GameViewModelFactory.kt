package com.example.geoguesserapp.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geoguesserapp.di.AppContainer

// Diese Factory erstellt das GameViewModel und übergibt ihm die benötigten Abhängigkeiten.
// Der AppContainer besitzt bereits alle Use Cases.
// Die Factory nimmt diese Objekte aus dem Container und gibt sie über den Konstruktor an das ViewModel weiter.
// Dadurch erstellt das GameViewModel seine Abhängigkeiten nicht selbst.
// Genau das ist der wichtige Teil unserer manuellen Dependency Injection.

class GameViewModelFactory(
    private val appContainer: AppContainer // Enthält alle Abhängigkeiten, die das GameViewModel benötigt.
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // Prüft, ob Android gerade ein GameViewModel anfordert.
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return GameViewModel(
                getRandomLocationsUseCase = appContainer.getRandomLocationsUseCase,
                calculateDistanceUseCase = appContainer.calculateDistanceUseCase,
                calculateLocationPointsUseCase = appContainer.calculateLocationPointsUseCase,
                calculateTimePointsUseCase = appContainer.calculateTimePointsUseCase,
                calculateYearPointsUseCase = appContainer.calculateYearPointsUseCase,
                calculateTotalPointsUseCase = appContainer.calculateTotalPointsUseCase,
                playerRepository = appContainer.playerRepository,
                authRepository = appContainer.authRepository
            ) as T
        }

        // Wird eine andere ViewModel Klasse angefordert, kann diese Factory sie nicht erstellen.
        throw IllegalArgumentException("Unbekannte ViewModel Klasse")
    }
}