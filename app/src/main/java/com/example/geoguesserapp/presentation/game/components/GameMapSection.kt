package com.example.geoguesserapp.presentation.game.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.geoguesserapp.R
import com.example.geoguesserapp.ui.theme.GeOdysseyTextSecondary
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite
import com.example.geoguesserapp.util.Constants
import org.maplibre.compose.camera.CameraState
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.expressions.dsl.image
import org.maplibre.compose.expressions.value.SymbolAnchor
import org.maplibre.compose.layers.SymbolLayer
import org.maplibre.compose.map.GestureOptions
import org.maplibre.compose.map.MapOptions
import org.maplibre.compose.map.MaplibreMap
import org.maplibre.compose.sources.GeoJsonData
import org.maplibre.compose.sources.rememberGeoJsonSource
import org.maplibre.compose.style.BaseStyle
import org.maplibre.compose.util.ClickResult
import org.maplibre.spatialk.geojson.Point
import org.maplibre.spatialk.geojson.Position

// Zeigt Kartenüberschrift, Bedienhinweis und die interaktive MapLibre Karte.
// Der CameraState kommt aus dem GameScreen, damit der Rundenwechsel die Kamera weiter zurücksetzen kann.
@Composable
fun GameMapSection(
    cameraState: CameraState,
    selectedLatitude: Double?,
    selectedLongitude: Double?,
    onLocationSelected: (latitude: Double, longitude: Double) -> Unit,
    onMapInteractionChanged: (Boolean) -> Unit
) {
    // Überschrift und kurze Bedienhilfe für die Karte.
    Text(
        text = stringResource(R.string.game_location_title),
        color = GeOdysseyWhite,
        style = MaterialTheme.typography.titleLarge
    )

    Text(
        text = stringResource(R.string.game_location_instruction),
        color = GeOdysseyTextSecondary,
        style = MaterialTheme.typography.bodyLarge
    )

    // Zeigt die interaktive Weltkarte.
    // Ein Klick speichert die gewählte Position im GameUiState.
    // Wenn bereits eine Position gewählt wurde, wird darauf ein Marker angezeigt.
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .pointerInput(Unit) {

                // MapLibre verarbeitet Verschieben, Zoom, Double Tap und Quick Zoom selbst.
                // Diese Beobachtung setzt nur den äußeren Scroll währenddessen aus.
                try {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()

                            onMapInteractionChanged(
                                event.changes.any { pointerInputChange ->
                                    pointerInputChange.pressed
                                }
                            )
                        }
                    }
                } finally {
                    onMapInteractionChanged(false)
                }
            },
        shape = RoundedCornerShape(22.dp)
    ) {
        MaplibreMap(
            modifier = Modifier.fillMaxSize(),
            baseStyle = BaseStyle.Uri(Constants.MAP_STYLE_URL),
            cameraState = cameraState,

            // Erlaubt Verschieben und Zoomen,
            // verhindert aber Rotation und Neigung.
            options = MapOptions(
                gestureOptions = GestureOptions(
                    isScrollEnabled = true,
                    isZoomEnabled = true,
                    isRotateEnabled = false,
                    isTiltEnabled = false,
                    isDoubleTapEnabled = true,
                    isQuickZoomEnabled = true
                )
            ),

            // Speichert die vom Spieler angeklickte Kartenposition.
            onMapClick = { position, _ ->

                onLocationSelected(
                    position.latitude,
                    position.longitude
                )

                // Der Kartenklick wurde verarbeitet,
                // nachdem die Position an den GameScreen zurückgemeldet wurde.
                ClickResult.Consume
            }
        ) {

            // Der Marker wird nur erzeugt,
            // wenn bereits ein Standort gewählt wurde.
            if (
                selectedLatitude != null &&
                selectedLongitude != null
            ) {

                val markerPosition = Position(
                    longitude = selectedLongitude,
                    latitude = selectedLatitude
                )

                // Erstellt einen einzelnen GeoJSON Punkt an der gewählten Position.
                val markerSource = rememberGeoJsonSource(
                    data = GeoJsonData.Features(
                        Point(markerPosition)
                    )
                )

                // Zeigt den abgegebenen Guess mit dem eigenen orangefarbenen Pin an.
                // Die untere Spitze des Pins liegt direkt auf der gewählten Kartenposition.
                SymbolLayer(
                    id = "guess-marker",
                    source = markerSource,
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
            }
        }
    }
}
