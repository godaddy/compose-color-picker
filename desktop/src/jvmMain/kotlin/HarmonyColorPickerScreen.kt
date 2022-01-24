import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.HsvColor
import com.godaddy.android.colorpicker.harmony.ColorHarmonyMode
import com.godaddy.android.colorpicker.harmony.HarmonyColorPicker

@Composable
fun HarmonyColorPickerScreen() {
    Column(modifier = Modifier.padding(8.dp)) {
        val currentColor = remember {
            mutableStateOf(Color.Black)
        }
        val extraColors = remember {
            mutableStateOf(emptyList<HsvColor>())
        }
        ColorPaletteBar(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            colors = listOf(HsvColor.from(currentColor.value)).plus(extraColors.value)
        )
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
            modifier = Modifier.defaultMinSize(minHeight = 300.dp, minWidth = 300.dp),
            onColorChanged = { hsvColor ->
                currentColor.value = hsvColor.toColor()
                extraColors.value = hsvColor.getColors(harmonyMode.value)
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColorPaletteBar(
    modifier: Modifier = Modifier,
    colors: List<HsvColor>
) {
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        cells = GridCells.Adaptive(48.dp),
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
