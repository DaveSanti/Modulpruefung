package com.example.geoguesserapp.domain.model

// Diese Datenklasse enthält alle Informationen für einen Standort im historischen Modus.
// Zusätzlich zu den gemeinsamen Standortdaten werden hier das Ereignis und das Jahr gespeichert.

data class HistoricalLocation(

    override val id: String, // Speichert die eindeutige ID des Standorts.
    override val name: String, // Speichert den Namen des Ortes.
    val event: String, // Speichert das historische Ereignis, das auf dem Bild dargestellt wird.
    val year: Int, // Speichert das Jahr. Negative Werte stehen für Jahre vor Christus.
    override val hint: String, // Speichert den Hinweis für den Spieler.
    override val source: String, // Speichert die Quelle des Bildes.
    override val imageResId: Int, // Speichert den Namen der Bildressource.
    override val longitude: Double, // Speichert den Längengrad des Standorts.
    override val latitude: Double // Speichert den Breitengrad des Standorts.
) : GameLocation