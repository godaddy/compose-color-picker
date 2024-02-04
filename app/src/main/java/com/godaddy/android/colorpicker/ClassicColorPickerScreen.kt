package com.godaddy.android.colorpicker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.godaddy.android.colorpicker.theme.BackButton
import com.godaddy.android.colorpicker.theme.ComposeColorPickerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassicColorPickerScreen(navController: NavController) {
    Column {
        TopAppBar(
            title = {
                Text(stringResource(R.string.classic_color_picker_sample))
            },
            navigationIcon = {
                BackButton { navController.navigateUp() }
            }
        )
        var currentColor by remember {
            mutableStateOf(HsvColor.from(Color.Red))
        }
        ColorPreviewInfo(currentColor = currentColor.toColor())
        ClassicColorPicker(
            color = currentColor,
            modifier = Modifier
                .height(300.dp)
                .padding(16.dp),
            onColorChanged = { hsvColor: HsvColor ->
                // Triggered when the color changes, do something with the newly picked color here!
                currentColor = hsvColor
            }
        )
    }
}

@Composable
fun ClassicColorPickerPreview() {
    ComposeColorPickerTheme {
        ClassicColorPicker(
            modifier = Modifier.height(300.dp),
            color = HsvColor.from(Color.Green),
            onColorChanged = {
            }
        )
    }
}

@Composable
fun ClassicColorPickerNoAlphaPreview() {
    ComposeColorPickerTheme {
        ClassicColorPicker(
            modifier = Modifier.height(300.dp),
            color = HsvColor.from(color = Color.Magenta),
            showAlphaBar = false,
            onColorChanged = {
            }
        )
    }
}
