package com.example.geoguesserapp

import android.app.Application
import com.example.geoguesserapp.di.AppContainer

// Diese Application Klasse wird erstellt, sobald die App gestartet wird.
// Hier erzeugen wir unseren AppContainer nur einmal für die gesamte Laufzeit der App.
// Dadurch müssen die Abhängigkeiten nicht bei jedem Screen oder ViewModel neu erstellt werden.
class GeoGuesserApplication : Application() {

    lateinit var appContainer: AppContainer // Speichert den zentralen Container mit den Abhängigkeiten der App.

    override fun onCreate() {
        super.onCreate()

        // Erstellt den AppContainer beim Start der App.
        // applicationContext sorgt dafür, dass kein einzelner Screen oder eine Activity gespeichert werden muss.
        appContainer = AppContainer(applicationContext)
    }
}