package com.aanealoneal.preferencehelper.app

import android.app.Application
import com.aanealoneal.preferencehelper.di.injectionModule
import org.koin.android.ext.android.startKoin


class App : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin(
            androidContext = this,
            modules = listOf(
                injectionModule,
            ),
            extraProperties = mapOf(),
            loadPropertiesFromFile = true
        )
    }
}