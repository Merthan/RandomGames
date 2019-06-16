package com.gmail.me2development.randomgames

import android.app.Application
import com.gmail.me2development.randomgames.data.Repository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module




class MainApplication :Application(){


    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO
            androidLogger()

            // use the Android context given there
            androidContext(this@MainApplication)

            // module
            modules(listOf(applicationModule))
        }
    }

}

val applicationModule= module {
    single { Repository() }
    factory { GameAdapter() }//Has connection to binding, so no singleton
    viewModel { MainViewModel(get()) }
}