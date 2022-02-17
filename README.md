# Android Jetpack Compose Color Picker 🎨

![Maven Central](https://img.shields.io/maven-central/v/com.godaddy.android.colorpicker/compose-color-picker-android?style=flat-square)

A component that provides two different HSV color pickers, written in Jetpack Compose.
1. ClassicColorPicker - Square picker with alpha channel
2. HarmonyColorPicker - Circular wheel with harmony modes (ie complementary, triadic, analogous, shades, monochromatic, tetradic)


https://user-images.githubusercontent.com/9973046/154516879-495a6816-9595-49b9-beaf-dafc2e1110ec.mp4

https://user-images.githubusercontent.com/9973046/154515203-f0818a14-3bb0-4e5a-91fc-f3cac2e2e770.mp4


## How to get started

Add the dependency to your `build.gradle` file:

```
implementation 'com.godaddy.android.colorpicker:compose-color-picker:<latest-version>'

// with Android ColorInt extensions
implementation 'com.godaddy.android.colorpicker:compose-color-picker-android:<latest-version>'
// desktop jvm version
implementation 'com.godaddy.android.colorpicker:compose-color-picker-jvm:<latest-version>'
```

Add `ClassicColorPicker` to your Compose hierarchy:

```kotlin
import com.godaddy.android.colorpicker.HsvColor

Column {
    ClassicColorPicker(
        onColorChanged = { color: HsvColor ->
            // Do something with the color
        }
    )
}
```

Or add the `HarmonyColorPicker` to your Compose hierarchy for an HSV color wheel implementation:

```kotlin
 HarmonyColorPicker(
    harmonyMode = harmonyMode.value,
    modifier = Modifier.size(400.dp),
    onColorChanged = { hsvColor ->
        currentColor.value = hsvColor.toColor()
        extraColors.value = hsvColor.getColors(colorHarmonyMode = harmonyMode.value)
})
```

The `HarmonyColorPicker` allows for you to set a certain `ColorHarmonyMode` on the wheel.
This will then display multiple magnifiers on top of the wheel for the different harmony modes: ie complementary, triadic, analogous, shades, monochromatic, tetradic.
If you wish to not display other magnifiers - set `ColorHarmonyMode.NONE` as your `harmonyMode` on the wheel.

# ClassicColorPicker:
## Customizing the control

### Size

To change the size of the control, pass in the `Modifier` option:

```kotlin
import com.godaddy.android.colorpicker.HsvColor

ClassicColorPicker(
    modifier = Modifier.height(200.dp),
    onColorChanged = { color: HsvColor ->
        // Do something with the color
    }
)
```

### Alpha

To hide the alpha bar, change the `showAlphaBar` parameter:

```kotlin
import com.godaddy.android.colorpicker.HsvColor

ClassicColorPicker(
    showAlphaBar = false,
    onColorChanged = { color: HsvColor ->
        // Do something with the color
    }
)
```

## HarmonyColorPicker

## Customizing the control

### Harmony Mode

To change the harmony mode of the picker, pass in a different mode into the function:

```kotlin
HarmonyColorPicker(
    harmonyMode = ColorHarmonyMode.SHADES,
    modifier = Modifier.size(400.dp),
    onColorChanged = { hsvColor ->
               // do stuff with new color
})
```

### Size

To change the size of the control, pass in the `Modifier` option:

```kotlin
import com.godaddy.android.colorpicker.HsvColor

HarmonyColorPicker(
    modifier = Modifier.height(200.dp),
    onColorChanged = { color: HsvColor ->
        // Do something with the color
    }
)
```

# Library Contribution Information

## Code Formatting

This project uses spotless to enforce code formatting. Run `./gradlew spotlessApply` to run formatting before committing.

### Releases

1. Update the version number in color-picker/build.gradle.kts
2. Make a PR into main and get that merged
3. Run "Deploy to Sonatype" GitHub Action.
4. Login to Sonatype and "Close" release. After a few minutes, click "Release".
5. Release should then be available for download on maven (might take like 30 min to propagate).
