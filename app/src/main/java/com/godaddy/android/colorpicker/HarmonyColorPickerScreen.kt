package com.godaddy.android.colorpicker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker
import com.godaddy.android.colorpicker.theme.BackButton

@Composable
fun HarmonyColorPickerScreen(navController: NavController) {
    Column {
        TopAppBar(
            title = {
                Text(stringResource(R.string.harmony_color_picker_sample))
            },
            navigationIcon = {
                BackButton { navController.navigateUp() }
            }
        )
        var currentColor by remember {
            mutableStateOf(HsvColor.from(Color.Black))
        }
        var extraColors by remember {
            mutableStateOf(emptyList<HsvColor>())
        }
        ColorPreviewInfo(currentColor = currentColor.toColor())
        var expanded by remember {
            mutableStateOf(false)
        }
        var harmonyMode by remember {
            mutableStateOf(ColorHarmonyMode.ANALOGOUS)
        }
        var showBrightnessBar by remember {
            mutableStateOf(true)
        }
        TextButton(onClick = {
            expanded = true
        }) {
            Text(harmonyMode.name)
        }
        DropdownMenu(expanded, onDismissRequest = {
            expanded = false
        }) {
            ColorHarmonyMode.values().forEach {
                DropdownMenuItem(onClick = {
                    harmonyMode = it
                    expanded = false
                }) {
                    Text(it.name)
                }
            }
        }
        HarmonyColorPicker(
            modifier = Modifier.size(400.dp),
            harmonyMode = harmonyMode,
            value = currentColor,
            fixedBrightness = 1f.takeIf { !showBrightnessBar }
        ) { color ->
            currentColor = color
            extraColors = color.getColors(colorHarmonyMode = harmonyMode)
        }
        ColorPaletteBar(modifier = Modifier.fillMaxWidth().height(70.dp), colors = extraColors)
        Row {
            Text("Show Brightness Bar")
            Switch(
                checked = showBrightnessBar,
                onCheckedChange = { checked ->
                    showBrightnessBar = checked
                }
            )
        }
        TextButton(onClick = { currentColor = HsvColor.from(Color.Green) }) {
            Text("Reset To Green")
        }
    }
}
