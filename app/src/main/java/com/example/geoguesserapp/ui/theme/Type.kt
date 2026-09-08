package com.example.geoguesserapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Zentrale Typografie von GeOdyssey.
// Große Überschriften und Score-Werte unterstützen den spielerischen Adventure-Look
// des UX Concept 2.0.
val Typography = Typography(

    // Große Titel wie GeOdyssey, Spiel beendet oder Leaderboard.
    displayLarge = TextStyle(
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 46.sp
    ),

    // Große Punktzahlen oder besonders wichtige Werte.
    displayMedium = TextStyle(
        fontSize = 34.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 40.sp
    ),

    // Hauptüberschriften eines Screens.
    headlineLarge = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 36.sp
    ),

    // Sekundäre Überschriften.
    headlineMedium = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 30.sp
    ),

    // Titel innerhalb größerer Cards.
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 28.sp
    ),

    // Untertitel beziehungsweise kleinere Card-Titel.
    titleMedium = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp
    ),

    // Standard-Fließtext.
    bodyLarge = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp
    ),

    // Sekundäre Informationen.
    bodyMedium = TextStyle(
        fontSize = 15.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 22.sp
    ),

    // Große Button-Texte.
    labelLarge = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 22.sp
    ),

    // Kleine Labels wie Rundenzähler oder Tags.
    labelMedium = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 18.sp
    )
)