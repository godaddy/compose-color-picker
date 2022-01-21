# Android Jetpack Compose Color Picker 🎨

![Maven Central](https://img.shields.io/maven-central/v/com.godaddy.android.colorpicker/compose-color-picker?style=flat-square)

A component that provides two different HSV color pickers, written in Jetpack Compose.
1. ClassicColorPicker - Square picker with alpha channel
2. HarmonyColorPicker - Circular wheel with harmony modes (ie complementary, triadic, analogous, shades, monochromatic, tetradic)

<img src="screenshots/ColorPicker.gif" width="200"  />
<img src="screenshots/ColorPicker-Harmony.gif" width="200"  />

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

Or Add the `HarmonyColorPicker` to your Compose hierarchy for a HSV circle color wheel implementation:

```kotlin
 HarmonyColorPicker(
    harmonyMode = harmonyMode.value,
    modifier = Modifier.size(400.dp),
    onColorChanged = { hsvColor ->
        currentColor.value = hsvColor.toColor()
        extraColors.value = hsvColor.getColors(colorHarmonyMode = harmonyMode.value) 
})
```

The `HarmonyColorPicker` allows for you to set a certain `ColorHarmonyMode` to enable the different options to be 
displayed on the picker: ie complementary, triadic, analogous, shades, monochromatic, tetradic or none. 

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

### Harmony Mode

To Change the harmony mode of the picker, pass in a different mode into the function:

```kotlin
HarmonyColorPicker(
    harmonyMode = ColorHarmonyMode.SHADES,
    modifier = Modifier.size(400.dp),
    onColorChanged = { hsvColor ->
               // do stuff with new color
})
```


# Library Contribution Information

### To make a release

1. Update the version number in color-picker/build.gradle.kts
2. Make a PR into main and get that merged
3. Run "Deploy to Sonatype" GitHub Action.
4. Login to Sonatype and "Close" release. After a few minutes, click "Release". 
5. Release should then be available for download on maven (might take like 30 min to propagate).

