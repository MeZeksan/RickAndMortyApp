package ru.mezeksan.rickandmortyapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.mezeksan.rickandmortyapp.di.appModule

class RickAndMortyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@RickAndMortyApp)
            modules(appModule)
        }
    }
}