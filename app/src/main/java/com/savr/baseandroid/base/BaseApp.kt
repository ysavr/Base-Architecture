package com.savr.baseandroid.base

import android.app.Application
import com.savr.baseandroid.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApp: Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun createStackElementTag(element: StackTraceElement): String {
                    return String.format(
                        "%s:%s: %s",
                        super.createStackElementTag(element),
                        element.methodName,
                        element.lineNumber
                    )
                }
            })
        }
    }
}