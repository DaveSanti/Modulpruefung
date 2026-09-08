package com.example.geoguesserapp.navigation

// Enthält alle Screens, zwischen denen innerhalb der App navigiert werden kann.
sealed class Screen(val route: String) {
    data object Start : Screen("start")
    data object Menu : Screen("menu")
    data object Game : Screen("game")
    data object RoundResult : Screen("round_result")
    data object EndResult : Screen("end_result")
    data object Tutorial : Screen("tutorial")
    data object Leaderboard : Screen("leaderboard")

}