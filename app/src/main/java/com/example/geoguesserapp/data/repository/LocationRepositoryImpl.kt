package com.example.geoguesserapp.data.repository

import com.example.geoguesserapp.data.datasource.LocationXmlDataSource
import com.example.geoguesserapp.domain.model.ClassicLocation
import com.example.geoguesserapp.domain.model.HistoricalLocation
import com.example.geoguesserapp.domain.repository.LocationRepository

// Diese Klasse setzt das LocationRepository konkret um.
// Sie verwendet unsere LocationXmlDataSource, um die Standortdaten aus den XML-Dateien zu laden.
// Die restliche App greift später nur auf das LocationRepository zu.
// Dadurch muss zum Beispiel das ViewModel nicht wissen, dass die Daten aus XML-Dateien stammen.
class LocationRepositoryImpl(
    private val dataSource: LocationXmlDataSource // Stellt den Zugriff auf die XML-Standortdaten bereit.
) : LocationRepository {

    // Gibt alle klassischen Standorte zurück, die von der DataSource geladen wurden.
    override fun getClassicLocations(): List<ClassicLocation> {
        return dataSource.loadClassicLocations()
    }

    // Gibt alle historischen Standorte zurück, die von der DataSource geladen wurden.
    override fun getHistoricalLocations(): List<HistoricalLocation> {
        return dataSource.loadHistoricalLocations()
    }
}
