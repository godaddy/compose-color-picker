package com.godaddy.android.colorpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker
import com.godaddy.android.colorpicker.theme.ComposeColorPickerTheme

@ExperimentalGraphicsApi
class SampleColorPickerActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeColorPickerTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val scrollState = rememberScrollState()
                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        TopAppBar(title = {
                            Text(stringResource(R.string.compose_color_picker_sample))
                        })
                        val currentColor = remember {
                            mutableStateOf(Color.Black)
                        }
                        ColorPreviewInfo(currentColor = currentColor.value)
                        val currentColorPicker = remember { mutableStateOf(ColorPicker.CLASSIC) }
                        TabRow(0, tabs = {
                            Text(
                                "Classic Picker", modifier =
                                Modifier.clickable {
                                    currentColorPicker.value = ColorPicker.CLASSIC
                                }
                            )
                            Text("Harmony Picker",
                                modifier = Modifier.clickable {
                                    currentColorPicker.value = ColorPicker.HARMONY
                                }
                            )
                        })
                        when (currentColorPicker.value) {
                            ColorPicker.CLASSIC -> {
                                // Here is how to add a Color Picker to your compose tree:
                                ClassicColorPicker(
                                    color = currentColor.value,
                                    modifier = Modifier
                                        .height(300.dp)
                                        .padding(16.dp),
                                    onColorChanged = { hsvColor: HsvColor ->
                                        // Triggered when the color changes, do something with the newly picked color here!
                                        currentColor.value = hsvColor.toColor()
                                    }
                                )
                            }
                            ColorPicker.HARMONY -> {

                                HarmonyColorPicker(
                                    harmonyMode = ColorHarmonyMode.COMPLEMENTARY,
                                    modifier = Modifier.size(400.dp),
                                    onColorChanged = { hsvColor ->
                                        currentColor.value = hsvColor.toColor()
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}

enum class ColorPicker {
    CLASSIC,
    HARMONY;
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


@ExperimentalGraphicsApi
@Composable
fun DefaultPreview() {
    ComposeColorPickerTheme {
        ClassicColorPicker(
            modifier = Modifier.height(300.dp),
            color = Color.Green,
            onColorChanged = {

            })

    }
}

@ExperimentalGraphicsApi
@Composable
fun NoAlphaBarPreview() {
    ComposeColorPickerTheme {
        ClassicColorPicker(
            modifier = Modifier.height(300.dp),
            color = Color.Magenta,
            showAlphaBar = false,
            onColorChanged = {

            })
    }
}