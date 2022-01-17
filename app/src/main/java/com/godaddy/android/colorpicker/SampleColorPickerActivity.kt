package com.godaddy.android.colorpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
                Surface(color = MaterialTheme.colors.background) {
                    val scrollState = rememberScrollState()
                    Column(modifier = Modifier.verticalScroll(scrollState)) {
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = "picker") {
                            composable("picker") { ColorPickerTypeScreen(navController) }
                            composable("classic") { ClassicColorPickerScreen(navController) }
                            composable("harmony") { HarmonyColorPickerScreen(navController) }
                        }
                    }
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
        Text("Classic Picker",
            modifier = Modifier.defaultMinSize(minHeight = 48.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate("classic")
                }.padding(8.dp)
        )
        Text("Harmony Picker",
            modifier = Modifier.defaultMinSize(minHeight = 48.dp)
                .fillMaxWidth()
                .clickable {
                    navController.navigate("harmony")
                }.padding(8.dp)
        )
    }
}