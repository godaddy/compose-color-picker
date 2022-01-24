
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Compose Color Pickers") {
        MaterialTheme {
            Surface(color = MaterialTheme.colors.background) {
                Column {
                    TopAppBar(title = {
                        Text("Compose Desktop Color Pickers")
                    })
                    // Here is how to add a Color Picker to your compose tree:

                    val currentColorPicker = remember { mutableStateOf(ColorPicker.CLASSIC) }
                    TabRow(
                        currentColorPicker.value.ordinal, tabs = {
                            Text(
                                "Classic Picker",
                                modifier =
                                Modifier.clickable {
                                    currentColorPicker.value = ColorPicker.CLASSIC
                                }.padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                "Harmony Picker",
                                modifier = Modifier.clickable {
                                    currentColorPicker.value = ColorPicker.HARMONY
                                }.padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                        },
                        contentColor = Color.White
                    )
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
