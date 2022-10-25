package com.godaddy.android.colorpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.godaddy.android.colorpicker.theme.ComposeColorPickerTheme

@ExperimentalGraphicsApi
class SampleColorPickerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeColorPickerTheme {
                var openDialog by remember { mutableStateOf(false) }
                var currentColor by remember {
                    mutableStateOf(HsvColor.from(Color.Black))
                }
                Surface(color = MaterialTheme.colors.background) {
                    val scrollState = rememberScrollState()
                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = Route.Picker.link) {
                            composable(Route.Picker.link) { ColorPickerTypeScreen(navController) }
                            composable(Route.ClassicColorPicker.link) { ClassicColorPickerScreen(navController) }
                            composable(Route.HarmonyColorPicker.link) { HarmonyColorPickerScreen(navController) }
                        }
                        Text(
                            text = "Dialog",
                            modifier = Modifier.clickable {
                                openDialog = true
                            }.padding(8.dp).fillMaxWidth()
                        )
                    }
                }
                if (openDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            openDialog = false
                        },
                        text = {
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
                        },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    openDialog = false
                                }
                            ) {
                                Text("Confirm")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    openDialog = false
                                }
                            ) {
                                Text("Dismiss")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ColorPickerTypeScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(title = {
            Text(stringResource(R.string.compose_color_picker_sample))
        })
        Text(
            stringResource(R.string.classic_color_picker_sample),
            modifier = Modifier.defaultMinSize(minHeight = 48.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Route.ClassicColorPicker.link)
                }.padding(8.dp)
        )
        Text(
            stringResource(R.string.harmony_color_picker_sample),
            modifier = Modifier.defaultMinSize(minHeight = 48.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Route.HarmonyColorPicker.link)
                }.padding(8.dp)
        )
    }
}
