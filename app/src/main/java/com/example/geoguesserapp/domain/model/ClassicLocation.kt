package com.example.geoguesserapp.domain.model

// Diese Datenklasse enthält alle Informationen für einen Standort im klassischen Modus.
// Zusätzlich zu den gemeinsamen Standortdaten werden hier Land und Kategorie gespeichert.

data class ClassicLocation(

    override val id: String, // Speichert die eindeutige ID des Standorts.
    override val name: String, // Speichert den Namen des Ortes.
    val country: String, // Speichert das Land, in dem sich der Standort befindet.
    val category: String, // Speichert die Kategorie des Standorts, zum Beispiel Natur oder Bauwerk.
    override val hint: String, // Speichert den Hinweis für den Spieler.
    override val source: String, // Speichert die Quelle des Bildes.
    override val imageResId: Int, // Speichert den Namen der Bildressource.
    override val longitude: Double, // Speichert den Längengrad des Standorts.
    override val latitude: Double // Speichert den Breitengrad des Standorts.
) : GameLocation