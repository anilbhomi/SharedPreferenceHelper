package com.aanealoneal.preferencehelper.di

import android.content.Context
import android.content.SharedPreferences
import com.aanealoneal.preferencehelper.preference.PreferenceName.preferenceName
import org.koin.dsl.module.module



val injectionModule = module {
    single { provideSharePreference(get(), preferenceName) }
}

fun provideSharePreference(context: Context, prefName: String): SharedPreferences = context.getSharedPreferences(
        prefName, Context.MODE_PRIVATE)


