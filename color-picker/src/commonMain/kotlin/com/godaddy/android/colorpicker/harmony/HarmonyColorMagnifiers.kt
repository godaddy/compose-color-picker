package com.godaddy.android.colorpicker.harmony

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import com.godaddy.android.colorpicker.HsvColor
import kotlin.math.cos
import kotlin.math.sin

@Composable
internal fun HarmonyColorMagnifiers(
    diameterPx: Int,
    hsvColor: HsvColor,
    currentlyDragging: Boolean,
    harmonyMode: ColorHarmonyMode
) {
    val size = IntSize(diameterPx, diameterPx)
    val position = positionForColor(hsvColor, size)

    val positionAnimated by animateOffsetAsState(
        targetValue = position,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
    )

    val diameterDp = with(LocalDensity.current) {
        diameterPx.toDp()
    }

    val animatedDiameter = animateDpAsState(
        targetValue = if (currentlyDragging) {
            diameterDp * diameterMainColorDragging
        } else {
            diameterDp * diameterMainColor
        }
    )

    hsvColor.getColors(harmonyMode).forEach { color ->
        val positionForColor by animateOffsetAsState(
            targetValue = positionForColor(color, size),
            animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
        )
        Magnifier(position = positionForColor, color = color, diameter = diameterDp * diameterHarmonyColor)
    }
    Magnifier(position = positionAnimated, color = hsvColor, diameter = animatedDiameter.value)
}

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

private const val diameterHarmonyColor = 0.10f
private const val diameterMainColorDragging = 0.18f
private const val diameterMainColor = 0.15f
