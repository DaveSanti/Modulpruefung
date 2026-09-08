package com.example.geoguesserapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

// Zentrales Material Design Farbschema von GeOdyssey.
// Das Theme ist bewusst dunkel gehalten, da das UX Concept 2.0
// auf allen Hauptscreens einen dunkelblauen Hintergrund verwendet.
private val GeOdysseyColorScheme = darkColorScheme(

    // Orange kennzeichnet die wichtigste Aktion eines Screens.
    primary = GeOdysseyOrange,
    onPrimary = GeOdysseyWhite,

    // Navy beziehungsweise Blau wird für sekundäre Aktionen und Orientierung verwendet.
    secondary = GeOdysseyBlue,
    onSecondary = GeOdysseyWhite,

    // Grün wird besonders für den historischen Modus und positive Zustände verwendet.
    tertiary = GeOdysseyGreen,
    onTertiary = GeOdysseyWhite,

    // Haupt-Hintergrundfarbe aller Screens.
    background = GeOdysseyNavy,
    onBackground = GeOdysseyWhite,

    // Standardfarbe für Cards und abgesetzte UI-Flächen.
    surface = GeOdysseyNavyLight,
    onSurface = GeOdysseyWhite,

    // Alternative Oberfläche für sekundäre Cards.
    surfaceVariant = GeOdysseyNavyLight,
    onSurfaceVariant = GeOdysseyTextSecondary,

    // Fehlerzustände.
    error = GeOdysseyError,
    onError = GeOdysseyWhite
)

// Wendet das zentrale GeOdyssey Theme auf die gesamte App an.
@Composable
fun GeoGuesserAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = GeOdysseyColorScheme,
        typography = Typography,
        content = content
    )
}