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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ExperimentalGraphicsApi
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.floor

/**
 * Alpha side bar Component that invokes onAlphaChanged when the value is mutated.
 *
 * @param modifier modifiers to set on the Alpha Bar
 * @param currentColor the initial color to set on the alpha bar.
 * @param onAlphaChanged the callback that is invoked when alpha value changes. 0 - 1.
 */
@ExperimentalGraphicsApi
@Composable
internal fun AlphaBar(
    modifier: Modifier = Modifier,
    currentColor: HsvColor,
    onAlphaChanged: (Float) -> Unit
) {

    val currentColorToAlphaBrush = remember(currentColor) {
        Brush.horizontalGradient(
            listOf(
                currentColor.copy(alpha = 1.0f).toColor(), Color(0x00ffffff)
            )
        )
    }
    Canvas(modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = { offset ->
                    if (!offset.isValid()) return@detectTapGestures
                    onAlphaChanged(getAlphaFromPosition(offset.x, this.size.width.toFloat()).coerceIn(0f, 1f))
                }
            )
        }
        .pointerInput(Unit) {
            detectDragGestures { pointerInput, _ ->
                if (!pointerInput.position.isValid()) return@detectDragGestures
                pointerInput.consumeAllChanges()
                onAlphaChanged(
                    getAlphaFromPosition(pointerInput.position.x, size.width.toFloat()).coerceIn(0f, 1f)
                )
            }
        }) {

        drawCheckeredBackground()
        drawAlphaBar(currentColorToAlphaBrush)

        val position = getPositionFromAlpha(
            color = currentColor,
            maxWidth = this.size.width
        )
        drawSelectorIndicator(amount = position, horizontal = true)
    }
}

internal fun DrawScope.drawSelectorIndicator(amount: Float, horizontal: Boolean) {
    val halfIndicatorThickness = 4.dp.toPx()
    val strokeThickness = 1.dp.toPx()

    val offset = if (horizontal) {
        Offset(
            x = amount - halfIndicatorThickness,
            y = - strokeThickness
        )
    } else {
        Offset(
            y = amount - halfIndicatorThickness,
            x = - strokeThickness
        )
    }
    val selectionSize = if (horizontal) {
        Size(halfIndicatorThickness * 2f, this.size.height + strokeThickness * 2)
    } else {
        Size(this.size.width + strokeThickness * 2, halfIndicatorThickness * 2f)
    }
    val selectionStyle = Stroke(strokeThickness)

    drawRect(
        Color.Gray,
        topLeft = offset,
        size = selectionSize,
        style = selectionStyle
    )
    drawRect(
        Color.White,
        topLeft = offset + Offset(strokeThickness, strokeThickness),
        size = selectionSize.inset(2 * strokeThickness),
        style = selectionStyle
    )
}

internal fun Size.inset(amount: Float): Size {
    return Size(width - amount, height - amount)
}

private fun DrawScope.drawAlphaBar(alphaBrush: Brush) {
    drawRect(alphaBrush)
    drawRect(Color.Gray, style = Stroke(0.5.dp.toPx()))
}

private fun DrawScope.drawCheckeredBackground() {
    val darkColor = Color.LightGray
    val lightColor = Color.White

    val gridSizePx = 8.dp.toPx()
    val cellCountX = floor(this.size.width / gridSizePx).toInt()
    val cellCountY = floor(this.size.height / gridSizePx).toInt()
    for (i in 0 until cellCountX) {
        for (j in 0 until cellCountY) {
            val color = if ((i + j) % 2 == 0) darkColor else lightColor

            val x = i * gridSizePx
            val y = j * gridSizePx
            drawRect(color, Offset(x, y), Size(gridSizePx, gridSizePx))
        }
    }
}

private fun getPositionFromAlpha(color: HsvColor, maxWidth: Float): Float {
    val alpha = 1 - color.alpha
    return maxWidth * alpha
}

/**
 * @return new alpha calculated from the maxWidth
 */
private fun getAlphaFromPosition(x: Float, maxWidth: Float): Float {
    return 1 - x / maxWidth
}
