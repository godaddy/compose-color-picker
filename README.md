# Android Jetpack Compose Color Picker 🎨

A component that provides an HSV color picker, written in Jetpack compose.

<img src="screenshots/ColorPicker.gif" width="200"  />

## How to get started

Add the dependency to your `build.gradle` file:

```
implementation `com.godaddy.android:compose-color-picker:0.1.0`
```

Add `ClassicColorPicker` to your Compose hierarchy:

```
import com.godaddy.android.colorpicker.HsvColor

Column {
    ClassicColorPicker(
        onColorChanged = { color: HsvColor ->
            // Do something with the color
        }
    )
}
```

## Customizing the control

### Size

To change the size of the control, pass in the `Modifier` option:

```
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

```
import com.godaddy.android.colorpicker.HsvColor

ClassicColorPicker(
    showAlphaBar = false,
    onColorChanged = { color: HsvColor ->
        // Do something with the color
    }
)
```

