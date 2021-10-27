package com.example.mygithubapp.di.component

import com.example.mygithubapp.di.module.NetworkModule
import com.example.mygithubapp.MainActivity
import com.example.mygithubapp.ReposActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(reposActivity: ReposActivity)
}