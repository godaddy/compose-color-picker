package com.godaddy.android.colorpicker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

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
