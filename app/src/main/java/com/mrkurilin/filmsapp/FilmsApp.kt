package com.mrkurilin.filmsapp

import android.app.Application
import com.mrkurilin.filmsapp.di.DaggerAppComponent

class FilmsApp : Application() {

    val appComponent by lazy {
        DaggerAppComponent.factory().create(context = this)
    }
}