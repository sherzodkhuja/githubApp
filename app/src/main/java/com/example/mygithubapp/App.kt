package com.example.mygithubapp

import android.app.Application
import com.example.mygithubapp.di.component.ApplicationComponent
import com.example.mygithubapp.di.component.DaggerApplicationComponent
import com.example.mygithubapp.di.module.NetworkModule

class App : Application() {

    lateinit var applicationComponent: ApplicationComponent

    companion object {
        lateinit var app: App
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        applicationComponent = DaggerApplicationComponent.builder()
            .networkModule(NetworkModule(applicationContext))
            .build()
    }
}