package com.godaddy.android.colorpicker

sealed class Route(val link: String) {
    object Picker : Route("picker")
    object ClassicColorPicker : Route("classic")
    object HarmonyColorPicker : Route("harmony")
}
