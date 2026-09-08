package com.example.geoguesserapp.domain.usecase

import com.example.geoguesserapp.domain.model.GameLocation
import com.example.geoguesserapp.domain.model.GameMode
import com.example.geoguesserapp.domain.repository.LocationRepository

// Dieser Use Case wählt die Standorte aus, die in einem neuen Spiel verwendet werden.
// Dadurch liegt die Auswahl der Spielorte nicht direkt im ViewModel, sondern getrennt in der Domain Schicht.
// Für jedes Spiel werden 5 zufällige und unterschiedliche Standorte ausgewählt.
// Welcher Standorttyp verwendet wird, hängt vom ausgewählten GameMode ab.

class GetRandomLocationsUseCase(
    private val repository: LocationRepository // Stellt alle verfügbaren Standorte bereit.
) {

    operator fun invoke(mode: GameMode): List<GameLocation> {

        // Abhängig vom Spielmodus wird zuerst die passende Standortliste geladen.
        val locations: List<GameLocation> = when (mode) {

            GameMode.CLASSIC -> repository.getClassicLocations()

            GameMode.HISTORICAL -> repository.getHistoricalLocations()
        }

        return locations
            .shuffled()      // shuffled() mischt die komplette Liste zufällig.
            .take(5)    // take(5) nimmt anschließend nur die ersten 5 Elemente der gemischten Liste.
                            // So kann ein Standort innerhalb eines Spiels nicht doppelt vorkommen.
    }
}