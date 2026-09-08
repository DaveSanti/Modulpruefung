package com.example.geoguesserapp.domain.repository

import com.example.geoguesserapp.domain.model.ClassicLocation
import com.example.geoguesserapp.domain.model.HistoricalLocation

// Dieses Interface legt fest, welche Standortdaten die restliche App anfordern kann.
// Es beschreibt nur, was bereitgestellt wird und nicht, woher die Daten tatsächlich kommen.
// Dadurch bleibt die Domain Schicht unabhängig von der konkreten Datenquelle.
// Aktuell kommen die Daten aus XML Dateien, später könnte die Datenquelle aber auch ausgetauscht werden.
interface LocationRepository {

    fun getClassicLocations(): List<ClassicLocation> // Gibt alle klassischen Standorte zurück.

    fun getHistoricalLocations(): List<HistoricalLocation> // Gibt alle historischen Standorte zurück.
}