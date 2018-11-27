package com.muslimlife.tf.footballappkotlang.extensions

import android.content.Context
import android.content.SharedPreferences
import com.muslimlife.tf.footballappkotlang.data.db.LocalSqliteOpenHelpster


// Access property for Context
val Context.localDb: LocalSqliteOpenHelpster
    get() = LocalSqliteOpenHelpster.instance(applicationContext)