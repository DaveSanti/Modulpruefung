package com.example.geoguesserapp.domain.model

// Gemeinsame Grundlage für klassische und historische Standorte.
// Beide Standorttypen besitzen diese Eigenschaften und können dadurch gemeinsam im Spiel verwendet werden.
interface GameLocation {
    val id: String // Speichert die eindeutige ID des Standorts.
    val name: String // Speichert den Namen des Ortes.
    val hint: String // Speichert den Hinweis für den Spieler.
    val source: String // Speichert die Quelle des verwendeten Bildes.
    val imageResId: Int // Speichert direkt die Android Ressourcen ID des Bildes aus res/drawable.
    val longitude: Double // Speichert den Längengrad des richtigen Standorts.
    val latitude: Double // Speichert den Breitengrad des richtigen Standorts.
}