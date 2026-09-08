package com.example.geoguesserapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.geoguesserapp.ui.theme.GeOdysseyNavy
import com.example.geoguesserapp.ui.theme.GeOdysseyOrange
import com.example.geoguesserapp.ui.theme.GeOdysseyWhite

// Zeigt ein Bild nahezu über den gesamten Bildschirm an.
// Die Component wird im historischen Modus verwendet,
// damit Details historischer Bilder besser betrachtet werden können.
@Composable
fun GeOdysseyFullscreenImage(
    imageResId: Int,
    contentDescription: String,
    closeText: String,
    onDismiss: () -> Unit
) {
    // Speichert den aktuellen Zoomfaktor des Bildes.
    // 1f entspricht der normalen Größe, 5f der maximalen Vergrößerung.
    var scale by remember {
        mutableFloatStateOf(1f)
    }

    // Speichert die horizontale Verschiebung des vergrößerten Bildes.
    var offsetX by remember {
        mutableFloatStateOf(0f)
    }

    // Speichert die vertikale Verschiebung des vergrößerten Bildes.
    var offsetY by remember {
        mutableFloatStateOf(0f)
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(GeOdysseyNavy)
                .padding(16.dp)
        ) {

            // Das Bild wird vollständig dargestellt.
            // Mit zwei Fingern kann hineingezoomt und das vergrößerte Bild verschoben werden.
            Image(
                painter = painterResource(imageResId),
                contentDescription = contentDescription,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 72.dp)
                    .pointerInput(Unit) {
                        // detectTransformGestures wertet Zoom und Verschieben gemeinsam aus.
                        // Dadurch kann der Spieler Details im historischen Bild genauer betrachten.
                        detectTransformGestures { _, pan, zoom, _ ->

                            // Begrenzung des Zooms zwischen normaler Größe und fünffacher Vergrößerung.
                            val newScale = (scale * zoom).coerceIn(
                                minimumValue = 1f,
                                maximumValue = 5f
                            )

                            // Das Bild kann nur verschoben werden,
                            // wenn es tatsächlich vergrößert wurde.
                            if (newScale > 1f) {
                                offsetX += pan.x
                                offsetY += pan.y
                            } else {

                                // Bei normaler Größe springt das Bild zurück in die Mitte.
                                offsetX = 0f
                                offsetY = 0f
                            }

                            scale = newScale
                        }
                    }
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offsetX
                        translationY = offsetY
                    }
            )

            // Schließt die Vollbildansicht.
            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GeOdysseyOrange,
                    contentColor = GeOdysseyWhite
                )
            ) {
                Text(
                    text = closeText,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
