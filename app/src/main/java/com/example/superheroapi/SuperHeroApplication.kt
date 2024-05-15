package com.example.superheroapi

import android.app.Application
import com.example.superheroapi.data.AppContainer
import com.example.superheroapi.data.DefaultAppContainer

class SuperHeroApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
