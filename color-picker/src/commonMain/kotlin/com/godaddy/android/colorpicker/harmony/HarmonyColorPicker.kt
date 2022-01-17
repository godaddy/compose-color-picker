package com.godaddy.android.colorpicker.harmony

import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.godaddy.android.colorpicker.HsvColor
import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.math.min

@Composable
fun HarmonyColorPicker(
    modifier: Modifier = Modifier,
    harmonyMode: ColorHarmonyMode,
    color: Color = Color.Red,
    onColorChanged: (HsvColor) -> Unit
) {
    BoxWithConstraints(modifier) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxHeight()
                .fillMaxWidth()
        ) {

            val hsvColor = remember { mutableStateOf(HsvColor.from(color)) }

            HarmonyColorPickerWithMagnifiers(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f),
                hsvColor = hsvColor,
                onColorChanged = {
                    hsvColor.value = it
                    onColorChanged(it)
                },
                harmonyMode = harmonyMode
            )

            BrightnessBar(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .weight(0.2f),
                onValueChange = { value ->
                    hsvColor.value = hsvColor.value.copy(value = value)
                    onColorChanged(hsvColor.value)
                },
                currentColor = hsvColor.value
            )
        }
    }
}

@Composable
private fun HarmonyColorPickerWithMagnifiers(
    modifier: Modifier = Modifier,
    hsvColor: State<HsvColor>,
    onColorChanged: (HsvColor) -> Unit,
    harmonyMode: ColorHarmonyMode
) {

    BoxWithConstraints(
        modifier = modifier
            .defaultMinSize(minWidth = 48.dp)
            .wrapContentSize()
            .aspectRatio(1f, matchHeightConstraintsFirst = true)

    ) {
        BoxWithConstraints {
            val diameterPx = constraints.maxWidth
            var currentlyDragging by remember {
                mutableStateOf(false)
            }
            val inputModifier = Modifier.pointerInput(Unit) {
                fun updateColorWheel(newPosition: Offset) {
                    // Work out if the new position is inside the circle we are drawing, and has a
                    // valid color associated to it. If not, keep the current position
                    val newColor = colorForPosition(newPosition, IntSize(diameterPx, diameterPx), hsvColor.value.value)
                    if (newColor != null) {
                        onColorChanged(newColor)
                    }
                }

                forEachGesture {
                    awaitPointerEventScope {
                        val down = awaitFirstDown(false)
                        currentlyDragging = true
                        updateColorWheel(down.position)
                        drag(down.id) { change ->
                            change.consumePositionChange()
                            updateColorWheel(change.position)
                        }
                        currentlyDragging = false
                    }
                }
            }

            Box(inputModifier.fillMaxSize()) {
                ColorWheel(hsvColor = hsvColor.value, diameter = diameterPx)
                HarmonyColorMagnifiers(diameterPx, hsvColor.value, currentlyDragging, harmonyMode)
            }
        }
    }
}

private fun colorForPosition(position: Offset, size: IntSize, value: Float): HsvColor? {
    val centerX: Double = size.width / 2.0
    val centerY: Double = size.height / 2.0
    val radius: Double = min(centerX, centerY)
    val xOffset: Double = position.x - centerX
    val yOffset: Double = position.y - centerY
    val centerOffset = hypot(xOffset, yOffset)
    val rawAngle = Math.toDegrees(atan2(yOffset, xOffset))
    val centerAngle = (rawAngle + 360.0) % 360.0
    return if (centerOffset <= radius) {
        HsvColor(
            hue = centerAngle.toFloat(),
            saturation = (centerOffset / radius).toFloat(),
            value = value,
            alpha = 1.0f
        )
    } else {
        null
    }
}
