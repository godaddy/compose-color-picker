import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor

@Composable
fun ClassicColorPickerScreen() {
    Column {
        var currentColor by remember {
            mutableStateOf(HsvColor.from(Color.Red))
        }
        ColorPreviewInfo(currentColor = currentColor.toColor())
        ClassicColorPicker(
            modifier = Modifier
                .height(300.dp)
                .padding(16.dp),
            color = currentColor,
            onColorChanged = { hsvColor: HsvColor ->
                // Triggered when the color changes, do something with the newly picked color here!
                currentColor = hsvColor
            }
        )
    }
}
