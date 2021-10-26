package com.godaddy.android.colorpicker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

/**
 * Saturation Value area Component that invokes onSaturationValueChanged when the saturation or value is mutated.
 *
 * @param modifier modifiers to set on the Alpha Bar
 * @param currentColor the initial color to set on the alpha bar.
 * @param onSaturationValueChanged the callback that is invoked when saturation or value component of the changes.
 * saturation, value both between 0 - 1.
 */
@ExperimentalGraphicsApi
@Composable
internal fun SaturationValueArea(
    modifier: Modifier = Modifier,
    currentColor: HsvColor,
    onSaturationValueChanged: (saturation: Float, value: Float) -> Unit
) {
    val blackGradientBrush = remember {
        Brush.verticalGradient(listOf(Color(0xffffffff), Color(0xff000000)))
    }

    val currentColorGradientBrush = remember(currentColor.hue) {
        Brush.horizontalGradient(
            listOf(
                Color(0xffffffff),
                Color.hsv(currentColor.hue, 1.0f, 1.0f)
            )
        )
    }

    Canvas(modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .pointerInput(Unit) {
            val size = this.size
            detectTapGestures(
                onTap = { newOffset ->
                    if (!newOffset.isValid()) return@detectTapGestures
                    val (s, v) = getSaturationPoint(newOffset, size)
                    onSaturationValueChanged(s, v)
                }
            )
        }
        .pointerInput(Unit) {
            detectDragGestures { newOffset, _ ->
                if (!newOffset.position.isValid()) return@detectDragGestures
                val (s, v) = getSaturationPoint(newOffset.position, size)
                onSaturationValueChanged(s, v)
            }
        }
    ) {
        drawRect(blackGradientBrush)
        drawRect(currentColorGradientBrush, blendMode = BlendMode.Multiply)
        drawRect(Color.Gray, style = Stroke(0.5.dp.toPx()))

        drawCircleSelector(currentColor)
    }
}

private fun DrawScope.drawCircleSelector(currentColor: HsvColor) {
    val radius = 6.dp
    val point = getSaturationValuePoint(currentColor, size = size)
    val circleStyle = Stroke(2.dp.toPx())
    drawCircle(
        color = Color.White,
        radius = radius.toPx(),
        center = point,
        style = circleStyle
    )
    drawCircle(
        color = Color.Gray,
        radius = (radius - 2.dp).toPx(),
        center = point,
        style = Stroke(1.dp.toPx())
    )
}

private fun getSaturationPoint(
    pressScope: Offset,
    size: IntSize
): Pair<Float, Float> {
    val (saturation, value) = getSaturationValueFromPosition(
        pressScope,
        size.toSize()
    )
    return saturation to value
}

/**
 * Gets the X/Y offset for a color based on the input Size
 * (This is for the large inner area)
 *
 * @return an Offset within the Size that represents the saturation and value of the supplied Color.
 */
private fun getSaturationValuePoint(color: HsvColor, size: Size): Offset {
    val height: Float = size.height
    val width: Float = size.width

    return Offset((color.saturation * width), (1f - color.value) * height)
}

/**
 * Given an offset and size, this function calculates a saturation and value amount based on that.
 *
 * @return new saturation and value
 */
private fun getSaturationValueFromPosition(offset: Offset, size: Size): Pair<Float, Float> {
    val width = size.width
    val height = size.height

    val newX = offset.x.coerceIn(0f, width)

    val newY = offset.y.coerceIn(0f, size.height)
    val saturation = 1f / width * newX
    val value = 1f - 1f / height * newY

    return saturation.coerceIn(0f, 1f) to value.coerceIn(0f, 1f)
}
