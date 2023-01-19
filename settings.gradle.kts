pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
rootProject.name = "ComposeColorPicker"
include (":app")
include (":desktop")
include (":jsApp")
include (":color-picker")
