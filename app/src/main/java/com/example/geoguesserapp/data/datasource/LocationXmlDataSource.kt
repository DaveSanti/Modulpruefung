package com.example.geoguesserapp.data.datasource

import android.content.Context
import com.example.geoguesserapp.R
import com.example.geoguesserapp.domain.model.ClassicLocation
import com.example.geoguesserapp.domain.model.HistoricalLocation
import org.xmlpull.v1.XmlPullParser

// Liest die klassischen und historischen Standortdaten aus den XML Dateien.
// Die XML Dateien enthalten alle Daten wie Ort, Hint, Koordinaten und die jeweilige Drawable Ressource.
// Klassische und historische Standorte werden getrennt eingelesen,
// da historische Einträge zusätzlich Jahr und Ereignis enthalten.
class LocationXmlDataSource(private val context: Context) {

    // Liest alle klassischen Standorte aus standorte_klassisch.xml.
    fun loadClassicLocations(): List<ClassicLocation> {
        val locations = mutableListOf<ClassicLocation>()
        val parser = context.resources.getXml(R.xml.standorte_klassisch)

        var id = ""
        var name = ""
        var country = ""
        var category = ""
        var hint = ""
        var source = ""
        var imageResId = 0
        var longitude = 0.0
        var latitude = 0.0

        var eventType = parser.eventType

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {

                XmlPullParser.START_TAG -> {
                    when (parser.name) {

                        // Bei jedem neuen Standort wird zuerst die ID aus dem Attribut gelesen.
                        "standort" -> id = parser.getAttributeValue(null, "id") ?: ""

                        "ort" -> name = parser.nextText()
                        "land" -> country = parser.nextText()
                        "kategorie" -> category = parser.nextText()
                        "hinweis" -> hint = parser.nextText()
                        "quelle" -> source = parser.nextText()

                        // Liest die Drawable Ressource direkt aus dem resource Attribut.
                        // Dadurch muss später nicht mehr über einen Bildnamen gesucht werden.
                        "bild" -> imageResId = parser.getAttributeResourceValue(null, "resource", 0)

                        // Die XML Dateien sind Teil des Projekts und werden kontrolliert gepflegt.
                        // Deshalb werden Koordinaten hier direkt in Double Werte umgewandelt.
                        "longitude" -> longitude = parser.nextText().toDouble()
                        "latitude" -> latitude = parser.nextText().toDouble()
                    }
                }

                XmlPullParser.END_TAG -> {
                    if (parser.name == "standort") {
                        locations.add(
                            ClassicLocation(
                                id = id,
                                name = name,
                                country = country,
                                category = category,
                                hint = hint,
                                source = source,
                                imageResId = imageResId,
                                longitude = longitude,
                                latitude = latitude
                            )
                        )
                    }
                }
            }

            eventType = parser.next()
        }

        parser.close()
        return locations
    }

    // Liest alle historischen Standorte aus standorte_historisch.xml.
    fun loadHistoricalLocations(): List<HistoricalLocation> {
        val locations = mutableListOf<HistoricalLocation>()
        val parser = context.resources.getXml(R.xml.standorte_historisch)

        var id = ""
        var year = 0
        var name = ""
        var event = ""
        var hint = ""
        var source = ""
        var imageResId = 0
        var longitude = 0.0
        var latitude = 0.0

        var eventType = parser.eventType

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {

                XmlPullParser.START_TAG -> {
                    when (parser.name) {

                        "standort" -> id = parser.getAttributeValue(null, "id") ?: ""

                        "jahr" -> year = parser.nextText().toInt()
                        "ort" -> name = parser.nextText()
                        "ereignis" -> event = parser.nextText()
                        "hinweis" -> hint = parser.nextText()
                        "quelle" -> source = parser.nextText()

                        // Auch hier wird die Drawable Ressourcen ID direkt aus der XML gelesen.
                        "bild" -> imageResId = parser.getAttributeResourceValue(null, "resource", 0)

                        // Jahr und Koordinaten werden direkt geparst,
                        // weil die historischen XML Daten feste Projektressourcen sind.
                        "longitude" -> longitude = parser.nextText().toDouble()
                        "latitude" -> latitude = parser.nextText().toDouble()
                    }
                }

                XmlPullParser.END_TAG -> {
                    if (parser.name == "standort") {
                        locations.add(
                            HistoricalLocation(
                                id = id,
                                name = name,
                                event = event,
                                year = year,
                                hint = hint,
                                source = source,
                                imageResId = imageResId,
                                longitude = longitude,
                                latitude = latitude
                            )
                        )
                    }
                }
            }

            eventType = parser.next()
        }

        parser.close()
        return locations
    }
}
