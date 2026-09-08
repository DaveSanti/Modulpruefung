package com.example.geoguesserapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.geoguesserapp.navigation.AppNavigation
import com.example.geoguesserapp.presentation.game.GameViewModel
import com.example.geoguesserapp.presentation.game.GameViewModelFactory
import com.example.geoguesserapp.presentation.leaderboard.LeaderboardViewModel
import com.example.geoguesserapp.presentation.leaderboard.LeaderboardViewModelFactory
import com.example.geoguesserapp.ui.theme.GeoGuesserAppTheme

// Die MainActivity ist der Einstiegspunkt der Benutzeroberfläche.
// Hier wird das GameViewModel mit unserer manuellen Dependency Injection erstellt
// und anschließend an die Navigation weitergegeben.
class MainActivity : ComponentActivity() {

    // Die Factory erhält den AppContainer aus der Application Klasse.
    // Dadurch bekommt das GameViewModel alle benötigten Use Cases von außen übergeben.
    private val gameViewModel: GameViewModel by viewModels {
        val app = application as GeoGuesserApplication
        GameViewModelFactory(app.appContainer)
    }

    // Auch das LeaderboardViewModel wird über denselben AppContainer erstellt.
    // Dadurch nutzt die Oberfläche weiterhin nur die bereitgestellte Repository Abhängigkeit.
    private val leaderboardViewModel: LeaderboardViewModel by viewModels {
        val app = application as GeoGuesserApplication
        LeaderboardViewModelFactory(app.appContainer)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GeoGuesserAppTheme {
                AppNavigation(
                    gameViewModel = gameViewModel,
                    leaderboardViewModel = leaderboardViewModel

                )
            }
        }
    }
}
