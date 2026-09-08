package com.example.geoguesserapp.presentation.result.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.geoguesserapp.R
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite
import com.example.geoguesserapp.util.Constants
import org.maplibre.compose.camera.rememberCameraState
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.expressions.dsl.image
import org.maplibre.compose.expressions.value.SymbolAnchor
import org.maplibre.compose.layers.LineLayer
import org.maplibre.compose.layers.SymbolLayer
import org.maplibre.compose.map.GestureOptions
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.sources.GeoJsonData
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.style.BaseStyle
import org.maplibre.spatialk.geojson.BoundingBox
import org.maplibre.spatialk.geojson.LineString
import org.maplibre.spatialk.geojson.Point
import org.maplibre.spatialk.geojson.Position

// Zeigt Guess und korrekten Standort auf der Ergebniskarte.
// Die Verbindungslinie macht die Entfernung zwischen beiden Positionen sichtbar.
@Composable
fun RoundResultMap(
    guessedLatitude: Double,
    guessedLongitude: Double,
    correctLatitude: Double,
    correctLongitude: Double
) {
    val guessedPosition = Position(
        longitude = guessedLongitude,
        latitude = guessedLatitude
    )

    val correctPosition = Position(
        longitude = correctLongitude,
        latitude = correctLatitude
    )

    // Verwaltet die sichtbare Kameraposition der Ergebniskarte.
    val cameraState = rememberCameraState()

    // Berechnet den geografischen Bereich zwischen Guess und richtigem Standort.
    // Dadurch können anschließend beide Positionen automatisch gleichzeitig auf der Ergebniskarte dargestellt werden.
    val resultBounds = BoundingBox(
        west = minOf(
            guessedLongitude,
            correctLongitude
        ),
        south = minOf(
            guessedLatitude,
            correctLatitude
        ),
        east = maxOf(
            guessedLongitude,
            correctLongitude
        ),
        north = maxOf(
            guessedLatitude,
            correctLatitude
        )
    )

    // Bewegt die Kartenkamera automatisch so, dass beide Pins sichtbar sind.
    // Das Padding verhindert, dass die Pins direkt am Kartenrand liegen.
    LaunchedEffect(resultBounds) {
        cameraState.animateTo(
            boundingBox = resultBounds,
            padding = PaddingValues(40.dp)
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp),
        shape = RoundedCornerShape(22.dp)
    ) {
        MaplibreMap(
            modifier = Modifier.fillMaxSize(),
            baseStyle = BaseStyle.Uri(
                Constants.MAP_STYLE_URL
            ),
            cameraState = cameraState,

            // Die Ergebniskarte dient hauptsächlich zur Betrachtung.
            // Verschieben und Zoomen bleiben trotzdem möglich, während Rotation und Neigung deaktiviert bleiben.
            options = MapOptions(
                gestureOptions = GestureOptions(
                    isScrollEnabled = true,
                    isZoomEnabled = true,
                    isRotateEnabled = false,
                    isTiltEnabled = false,
                    isDoubleTapEnabled = true,
                    isQuickZoomEnabled = true
                )
            )
        ) {
            // MapLibre zeichnet Punkte und Linien über GeoJSON Quellen.
            // Jede Quelle beschreibt genau ein sichtbares Kartenelement.
            val guessSource = rememberGeoJsonSource(
                data = GeoJsonData.Features(
                    Point(guessedPosition)
                )
            )

            val correctSource = rememberGeoJsonSource(
                data = GeoJsonData.Features(
                    Point(correctPosition)
                )
            )

            val lineSource = rememberGeoJsonSource(
                data = GeoJsonData.Features(
                    LineString(
                        listOf(
                            guessedPosition,
                            correctPosition
                        )
                    )
                )
            )

            // Zeichnet zuerst die Verbindungslinie.
            // Dadurch werden die beiden Pins anschließend darüber dargestellt.
            LineLayer(
                id = "result-line",
                source = lineSource,
                color = const(GeOdysseyWhite),
                width = const(3.dp)
            )

            // Durch SymbolAnchor Bottom liegt die Spitze des Pins genau auf der gewählten Position.
            SymbolLayer(
                id = "guess-position",
                source = guessSource,
                iconImage = image(
                    value = painterResource(R.drawable.pin_guess),
                    size = DpSize(
                        width = 22.dp,
                        height = 47.dp
                    )
                ),
                iconAnchor = const(SymbolAnchor.Bottom),
                iconAllowOverlap = const(true)
            )

            // Auch beim Ziel Pin liegt die untere Spitze exakt auf der korrekten Position.
            SymbolLayer(
                id = "correct-position",
                source = correctSource,
                iconImage = image(
                    value = painterResource(R.drawable.pin_target),
                    size = DpSize(
                        width = 22.dp,
                        height = 47.dp
                    )
                ),
                iconAnchor = const(SymbolAnchor.Bottom),
                iconAllowOverlap = const(true)
            )
        }
    }
}
