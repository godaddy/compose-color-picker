package com.godaddy.android.colorpicker

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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

@OptIn(ExperimentalMaterial3Api::class)
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
            mutableStateOf(HsvColor.from(Color.Red))
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
            ColorHarmonyMode.entries.forEach {
                DropdownMenuItem(
                    onClick = {
                        harmonyMode = it
                        expanded = false
                    },
                    text = {
                        Text(it.name)
                    }
                )
            }
        }
        Column(Modifier.width(IntrinsicSize.Min)) {
            HarmonyColorPicker(
                modifier = Modifier.size(400.dp),
                harmonyMode = harmonyMode,
                color = currentColor,
            ) { color ->
                currentColor = color
                extraColors = color.getColors(colorHarmonyMode = harmonyMode)
            }
            if (showBrightnessBar) {
                Slider(
                    modifier = Modifier.padding(16.dp),
                    value = currentColor.value,
                    onValueChange = { currentColor = currentColor.copy(value = it) }
                )
            }
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
