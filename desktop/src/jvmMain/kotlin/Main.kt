import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        MaterialTheme {
            Surface(color = MaterialTheme.colors.background) {
                Column {
                    TopAppBar(title = {
                        Text("Compose Desktop color picker")
                    })
                    // Here is how to add a Color Picker to your compose tree:

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
                    }, contentColor = Color.White, modifier = Modifier.height(48.dp))
                    when (currentColorPicker.value) {
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