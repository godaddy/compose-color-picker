package com.godaddy.android.colorpicker

import android.app.Application
import android.os.StrictMode

class SampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder().detectAll()
                .penaltyLog()
                .penaltyFlashScreen()
                .build(),
        )

        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyDeath()
                .penaltyLog()
                .build(),
        )
    }
}