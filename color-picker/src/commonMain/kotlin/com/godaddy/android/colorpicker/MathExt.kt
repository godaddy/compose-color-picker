package com.godaddy.android.colorpicker

internal fun Float.toRadian(): Float = this / 180.0f * Math.PI.toFloat()
internal fun Double.toRadian(): Double = this / 180 * Math.PI
internal fun Float.toDegree(): Float = this * 180.0f / Math.PI.toFloat()
internal fun Double.toDegree(): Double = this * 180 / Math.PI