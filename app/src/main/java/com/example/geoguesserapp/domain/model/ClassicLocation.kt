package com.example.geoguesserapp.domain.model

// Diese Datenklasse enthält alle Informationen für einen Standort im klassischen Modus.
// Zusätzlich zu den gemeinsamen Standortdaten werden hier Land und Kategorie gespeichert.

data class ClassicLocation(

    // Gemeinsame Standortdaten.
    override val id: String, // Speichert die eindeutige ID des Standorts.
    override val name: String, // Speichert den Namen des Ortes.

    // Zusätzliche Informationen für den klassischen Modus.
    val country: String, // Speichert das Land, in dem sich der Standort befindet.
    val category: String, // Speichert die Kategorie des Standorts, zum Beispiel Natur oder Bauwerk.

    // Anzeige- und Hilfsdaten für den Guess.
    override val hint: String, // Speichert den Hinweis für den Spieler.
    override val source: String, // Speichert die Quelle des Bildes.
    override val imageResId: Int, // Speichert die Android Ressourcen ID der Bildressource.

    // Richtige Kartenposition des Standorts.
    override val longitude: Double, // Speichert den Längengrad des Standorts.
    override val latitude: Double // Speichert den Breitengrad des Standorts.
) : GameLocation
