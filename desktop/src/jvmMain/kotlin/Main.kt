import androidx.compose.desktop.DesktopMaterialTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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

@OptIn(ExperimentalGraphicsApi::class)
fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        DesktopMaterialTheme {
            Surface(color = MaterialTheme.colors.background) {
                Column {
                    TopAppBar(title = {
                        Text("compose desktop color picker")
                    })
                    val currentColor = remember {
                        mutableStateOf(Color.Red)
                    }
                    ColorPreviewInfo(currentColor = currentColor.value)

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