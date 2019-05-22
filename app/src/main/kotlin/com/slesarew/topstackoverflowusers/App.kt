package com.slesarew.topstackoverflowusers

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import net.danlew.android.joda.JodaTimeAndroid

class App : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

        App.baseContext = base
    }

    override fun onCreate() {
        super.onCreate()

        JodaTimeAndroid.init(this)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var baseContext: Context
    }
}