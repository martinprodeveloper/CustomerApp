package com.martinprodeveloper.customerapp

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setLocale(this, "es")
    }

    private fun setLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        // For API 24 and above, use the createConfigurationContext method
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            context.createConfigurationContext(config)
        } else {
            // For API below 24, you need to update the resources directly
            context.resources.updateConfiguration(config, context.resources.displayMetrics)
        }
    }
}