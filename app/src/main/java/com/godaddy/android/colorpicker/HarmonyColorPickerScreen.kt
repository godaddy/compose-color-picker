package com.godaddy.android.colorpicker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
        val currentColor = remember {
            mutableStateOf(Color.Black)
        }
        val extraColors = remember {
            mutableStateOf(emptyList<HsvColor>())
        }
        ColorPreviewInfo(currentColor = currentColor.value)
        val expanded = remember {
            mutableStateOf(false)
        }
        val harmonyMode = remember {
            mutableStateOf(ColorHarmonyMode.ANALOGOUS)
        }
        TextButton(onClick = {
            expanded.value = true
        }) {
            Text(harmonyMode.value.name)
        }
        DropdownMenu(expanded.value, onDismissRequest = {
            expanded.value = false
        }) {
            ColorHarmonyMode.values().forEach {
                DropdownMenuItem(onClick = {
                    harmonyMode.value = it
                    expanded.value = false
                }) {
                    Text(it.name)
                }
            }
        }
        HarmonyColorPicker(
            harmonyMode = harmonyMode.value,
            modifier = Modifier.size(400.dp),
            onColorChanged = { hsvColor ->
                currentColor.value = hsvColor.toColor()
                extraColors.value = hsvColor.getColors(colorHarmonyMode = harmonyMode.value)
            }
        )
        ColorPaletteBar(modifier = Modifier.fillMaxWidth().height(70.dp), colors = extraColors.value)
    }
}
