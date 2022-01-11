package com.godaddy.android.colorpicker.harmony

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.HsvColor
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun HarmonyColorMagnifiers(
    diameter: Int,
    hsvColor: HsvColor,
    currentlyDragging: Boolean,
    harmonyMode: ColorHarmonyMode
) {
    val size = IntSize(diameter, diameter)
    val position = positionForColor(hsvColor, size)

    val positionAnimated by animateOffsetAsState(
        targetValue = position,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
    )

    val animatedDiameter = animateDpAsState(
        targetValue = if (currentlyDragging) {
            SelectionCircleDiameterLarger
        } else {
            SelectionCircleDiameterLarge
        }
    )

    hsvColor.getColors(harmonyMode).forEach { color ->
        val positionForColor by animateOffsetAsState(
            targetValue = positionForColor(color, size),
            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
        )

        Magnifier(position = positionForColor, color = color, diameter = SelectionCircleDiameter)
    }
    Magnifier(position = positionAnimated, color = hsvColor, diameter = animatedDiameter.value)
}

private val SelectionCircleDiameter = 30.dp
private val SelectionCircleDiameterLarge = 40.dp
private val SelectionCircleDiameterLarger = 48.dp

private fun positionForColor(color: HsvColor, size: IntSize): Offset {
    val radians = Math.toRadians(color.hue.toDouble())
    val phi = color.saturation
    val x: Float = ((phi * cos(radians)).toFloat() + 1) / 2f
    val y: Float = ((phi * sin(radians)).toFloat() + 1) / 2f
    return Offset(
        x = (x * size.width),
        y = (y * size.height)
    )
}
