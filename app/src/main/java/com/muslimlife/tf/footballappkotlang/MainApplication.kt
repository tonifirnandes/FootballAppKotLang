package com.muslimlife.tf.footballappkotlang

import android.app.Application
import android.content.Context
import com.google.gson.GsonBuilder
import com.muslimlife.tf.footballappkotlang.data.preference.SharedPrefs

class MainApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // TODO find better practice for creating object before KoinContext init
        base?.let {
            SharedPrefs.init(it, GsonBuilder().create())
        }
    }
}