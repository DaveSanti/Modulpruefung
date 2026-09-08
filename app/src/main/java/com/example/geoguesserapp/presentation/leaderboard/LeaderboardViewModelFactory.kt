package com.example.geoguesserapp.presentation.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geoguesserapp.di.AppContainer

// Erstellt das LeaderboardViewModel und übergibt ihm die benötigte Repository Abhängigkeit.
// Dadurch wird das Repository nicht innerhalb des ViewModels selbst erzeugt.
class LeaderboardViewModelFactory(
    private val appContainer: AppContainer
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(LeaderboardViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return LeaderboardViewModel(
                leaderboardRepository = appContainer.leaderboardRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}