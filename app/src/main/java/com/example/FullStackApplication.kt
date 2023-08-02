package com.example

import android.app.Application
import com.example.fullstacktry.data.AppContainer
import com.example.fullstacktry.data.DefaultAppContainer

class FullStackApplication:Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}