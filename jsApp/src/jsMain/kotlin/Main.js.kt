/*
* Copyright 2020-2021 JetBrains s.r.o. and respective authors and developers.
* Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE.txt file.
*/

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import org.jetbrains.skiko.wasm.onWasmReady

fun main() {
    onWasmReady {
        Window("Compose Color Picker") {
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        TopAppBar(title = {
                            Text("Compose Desktop Color Pickers")
                        })
                        // Here is how to add a Color Picker to your compose tree:

                        var currentColorPicker by remember { mutableStateOf(ColorPicker.CLASSIC) }
                        TabRow(
                            currentColorPicker.ordinal,
                            tabs = {
                                Text(
                                    "Classic Picker",
                                    modifier =
                                    Modifier.clickable {
                                        currentColorPicker = ColorPicker.CLASSIC
                                    }.padding(16.dp),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    "Harmony Picker",
                                    modifier = Modifier.clickable {
                                        currentColorPicker = ColorPicker.HARMONY
                                    }.padding(16.dp),
                                    textAlign = TextAlign.Center
                                )
                            },
                            contentColor = Color.White
                        )
                        when (currentColorPicker) {
                            ColorPicker.CLASSIC -> {
                                ClassicColorPickerScreen()
                            }
                            ColorPicker.HARMONY -> {
                                HarmonyColorPickerScreen()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ColorPreviewInfo(currentColor: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "a: ${currentColor.alpha} \n" +
                "r: ${currentColor.red} \n" +
                "g: ${currentColor.green} \n" +
                "b: ${currentColor.blue}"
        )
        Spacer(
            modifier = Modifier
                .background(
                    currentColor,
                    shape = CircleShape
                )
                .size(48.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
    }
}

enum class ColorPicker {
    CLASSIC,
    HARMONY;
}
