import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.HsvColor
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker

@Composable
fun HarmonyColorPickerScreen() {
    Column(modifier = Modifier.padding(8.dp)) {
        var currentColor by remember {
            mutableStateOf(HsvColor.from(Color.Red))
        }
        var extraColors by remember {
            mutableStateOf(emptyList<HsvColor>())
        }
        ColorPaletteBar(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            colors = listOf(currentColor).plus(extraColors)
        )
        var expanded by remember {
            mutableStateOf(false)
        }
        var harmonyMode by remember {
            mutableStateOf(ColorHarmonyMode.ANALOGOUS)
        }
        TextButton(onClick = {
            expanded = true
        }) {
            Text(harmonyMode.name)
        }
//        DropdownMenu(expanded, onDismissRequest = {
//            expanded = false
//        }) {
//            ColorHarmonyMode.values().forEach {
//                DropdownMenuItem(onClick = {
//                    harmonyMode = it
//                    expanded = false
//                }) {
//                    Text(it.name)
//                }
//            }
//        }
        HarmonyColorPicker(
            harmonyMode = harmonyMode,
            modifier = Modifier.defaultMinSize(minHeight = 300.dp, minWidth = 300.dp),
            color = currentColor,
            onColorChanged = { hsvColor ->
                currentColor = hsvColor
                extraColors = hsvColor.getColors(harmonyMode)
            }
        )
    }
}

@Composable
fun ColorPaletteBar(
    modifier: Modifier = Modifier,
    colors: List<HsvColor>
) {
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        columns = GridCells.Adaptive(48.dp),
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        content = {
            items(colors) { color ->
                Canvas(modifier = Modifier.size(48.dp)) {
                    drawCircle(color.toColor())
                }
            }
        }
    )
}
