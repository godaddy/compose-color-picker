# Android Jetpack Compose Color Picker 🎨

A component that provides an HSV color picker, written in Jetpack compose.

<img src="screenshots/ColorPicker.gif" width="200"  />

## How to get started

Add the dependency to your `build.gradle` file:

```
implementation `TODO add library published details here`
```

Add `ClassicColorPicker` to your Compose hierarchy:

```
Column {
    ClassicColorPicker(
        onColorChanged = { color ->
            // Do something with the colour
        }
    )
}
```

## Customizing the control

### Size

To change the size of the control, pass in the `Modifier` option:

```
ClassicColorPicker(
    modifier = Modifier.height(200.dp),
    onColorChanged = { color ->
        // Do something with the colour
    }
)
```

### Alpha

To hide the alpha bar, change the `showAlphaBar` parameter:

```
ClassicColorPicker(
    showAlphaBar = false,
    onColorChanged = { color ->
        // Do something with the colour
    }
)
```

