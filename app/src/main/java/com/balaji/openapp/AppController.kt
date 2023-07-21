package com.balaji.openapp

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication


class AppController : MultiDexApplication() {

    companion object {
        var instance: AppController? = null
            private set
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        SharedPreferencesManager.init(this)
    }
}