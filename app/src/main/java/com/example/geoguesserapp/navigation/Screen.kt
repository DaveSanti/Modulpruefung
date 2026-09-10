package com.example.geoguesserapp.navigation

// Enthält alle Screens, zwischen denen innerhalb der App navigiert werden kann.
// Jede Route wird zentral definiert, damit die Navigation keine freien Stringwerte verteilt.
sealed class Screen(val route: String) {
    // Einstieg der App mit Namenseingabe.
    data object Start : Screen("start")

    // Hauptmenü mit Modusauswahl, Tutorial und Leaderboard.
    data object Menu : Screen("menu")

    // Aktive Spielrunde mit Bild, Hint, Eingabe und Karte.
    data object Game : Screen("game")

    // Ergebnis einer einzelnen Runde.
    data object RoundResult : Screen("round_result")

    // Gesamtergebnis nach allen Runden.
    data object EndResult : Screen("end_result")

    // Anleitung für klassischen und historischen Modus.
    data object Tutorial : Screen("tutorial")

    // Online Leaderboard aus Firebase.
    data object Leaderboard : Screen("leaderboard")

}
