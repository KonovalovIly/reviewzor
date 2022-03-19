package ru.ssau.reviewzor.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.ssau.reviewzor.di.appModule
import ru.ssau.reviewzor.di.dataModule
import ru.ssau.reviewzor.di.domainModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }
}