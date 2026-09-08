package com.example.geoguesserapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.geoguesserapp.domain.model.GameMode
import com.example.geoguesserapp.presentation.game.GameScreen
import com.example.geoguesserapp.presentation.game.GameViewModel
import com.example.geoguesserapp.presentation.leaderboard.LeaderboardScreen
import com.example.geoguesserapp.presentation.leaderboard.LeaderboardViewModel
import com.example.geoguesserapp.presentation.menu.MenuScreen
import com.example.geoguesserapp.presentation.result.EndResultScreen
import com.example.geoguesserapp.presentation.result.RoundResultScreen
import com.example.geoguesserapp.presentation.start.StartScreen
import com.example.geoguesserapp.presentation.tutorial.TutorialScreen

// Steuert die Navigation zwischen allen Screens der App.
// Das gemeinsame GameViewModel enthält den Spielzustand und bleibt während des gesamten Spiels erhalten.
// Das LeaderboardViewModel verwaltet die gespeicherten Highscores.
@Composable
fun AppNavigation(
    gameViewModel: GameViewModel,
    leaderboardViewModel: LeaderboardViewModel
) {
    val navController = rememberNavController()
    val uiState by gameViewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = Screen.Start.route
    ) {

        // Startscreen für die Eingabe des Spielernamens.
        composable(Screen.Start.route) {
            StartScreen(
                onContinue = { playerName ->
                    gameViewModel.setPlayerName(playerName)
                    navController.navigate(Screen.Menu.route)
                }
            )
        }

        // Hauptmenü mit den beiden Spielmodi, Tutorial und Leaderboard.
        composable(Screen.Menu.route) {
            MenuScreen(
                onClassicMode = {
                    gameViewModel.setGameMode(GameMode.CLASSIC)
                    gameViewModel.startGame()
                    navController.navigate(Screen.Game.route)
                },
                onHistoricalMode = {
                    gameViewModel.setGameMode(GameMode.HISTORICAL)
                    gameViewModel.startGame()
                    navController.navigate(Screen.Game.route)
                },
                onTutorial = {
                    navController.navigate(Screen.Tutorial.route)
                },
                onLeaderboard = {
                    navController.navigate(Screen.Leaderboard.route)
                },
                onChangeName = {
                    navController.navigate(Screen.Start.route) {
                        popUpTo(Screen.Menu.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Zeigt den aktuell laufenden Guess an.
        composable(Screen.Game.route) {

            // Sobald showRoundResult true wird, wird automatisch zum Rundenergebnis navigiert.
            // Dadurch funktioniert die Navigation auch dann, wenn der Timer automatisch abläuft.
            LaunchedEffect(uiState.showRoundResult) {
                if (uiState.showRoundResult) {
                    navController.navigate(Screen.RoundResult.route)
                }
            }

            GameScreen(
                gameViewModel = gameViewModel
            )
        }

        // Zeigt nach jedem Guess das Ergebnis der aktuellen Runde.
        composable(Screen.RoundResult.route) {
            val lastResult = uiState.results.lastOrNull()

            if (lastResult != null) {
                RoundResultScreen(
                    result = lastResult,
                    onContinue = {

                        // Speichert vor der State Änderung, ob gerade Runde 5 beendet wurde.
                        val gameFinished = uiState.isGameFinished

                        gameViewModel.continueAfterRound()

                        if (gameFinished) {

                            // Nach Runde 5 wird zunächst der RoundResultScreen angezeigt.
                            // Danach wird zum Gesamtergebnis navigiert.
                            navController.navigate(Screen.EndResult.route) {
                                popUpTo(Screen.Game.route) {
                                    inclusive = true
                                }
                            }

                        } else {

                            // Nach Runde 1 bis 4 wird zum bereits vorhandenen GameScreen zurückgekehrt.
                            navController.popBackStack()
                        }
                    }
                )
            }
        }

        // Zeigt nach Runde 5 das Gesamtergebnis an.
        composable(Screen.EndResult.route) {
            var isNewPersonalHighscore by remember {
                mutableStateOf(false)
            }

            // Speichert den erreichten Highscore einmal beim Öffnen des EndResultScreens.
            // Zusätzlich zum Spielernamen und Score wird auch der Spielmodus gespeichert,
            // damit klassisches und historisches Leaderboard getrennt bleiben.
            // Unit sorgt dafür, dass dieser Speichervorgang nicht bei jeder Recomposition erneut startet.
            LaunchedEffect(Unit) {
                val gameMode = uiState.gameMode

                if (
                    uiState.playerName.isNotBlank() &&
                    uiState.isGameFinished &&
                    gameMode != null
                ) {
                    isNewPersonalHighscore = leaderboardViewModel.saveScore(
                        playerName = uiState.playerName,
                        gameMode = gameMode,
                        score = uiState.totalScore
                    )
                }
            }

            EndResultScreen(
                totalScore = uiState.totalScore,
                gameMode = uiState.gameMode,
                isNewPersonalHighscore = isNewPersonalHighscore,

                onPlayAgain = {
                    gameViewModel.startGame()

                    navController.navigate(Screen.Game.route) {
                        popUpTo(Screen.EndResult.route) {
                            inclusive = true
                        }
                    }
                },

                onBackToMenu = {
                    navController.navigate(Screen.Menu.route) {
                        popUpTo(Screen.Menu.route) {
                            inclusive = false
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Tutorial.route) {
            TutorialScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Zeigt das Leaderboard an.
        // Der LeaderboardScreen kann selbst zwischen Klassisch und Historisch wechseln.
        composable(Screen.Leaderboard.route) {
            LeaderboardScreen(
                leaderboardViewModel = leaderboardViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
