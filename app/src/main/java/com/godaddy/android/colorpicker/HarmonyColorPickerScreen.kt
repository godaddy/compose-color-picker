package com.godaddy.android.colorpicker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
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
        TopAppBar(title = {
            Text(stringResource(R.string.harmony_color_picker_sample))
        },
        navigationIcon = {
            BackButton { navController.navigateUp() }
        })
        val currentColor = remember {
            mutableStateOf(Color.Black)
        }
        ColorPreviewInfo(currentColor = currentColor.value)
        val expanded = remember {
            mutableStateOf(false)
        }
        val harmonyMode = remember {
            mutableStateOf(ColorHarmonyMode.ANALOGOUS)
        }
        Button(onClick = {
            expanded.value = true
        }) {
            Text(harmonyMode.value.name)
        }
        DropdownMenu(expanded.value, onDismissRequest =  {
            expanded.value = false
        }) {
            ColorHarmonyMode.values().forEach {
                DropdownMenuItem(onClick =  {
                    harmonyMode.value = it
                    expanded.value = false
                }) {
                    Text(it.name, modifier = Modifier.clickable {
                        harmonyMode.value = it
                        expanded.value = false
                    })
                }
            }
        }
        HarmonyColorPicker(
            harmonyMode = harmonyMode.value,
            modifier = Modifier.size(400.dp),
            onColorChanged = { hsvColor ->
                currentColor.value = hsvColor.toColor()
            })
    }

}