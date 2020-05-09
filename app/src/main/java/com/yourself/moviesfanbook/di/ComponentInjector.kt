package com.yourself.moviesfanbook.di

import android.app.Application
import android.content.Context
import com.yourself.searchyourcityweather.di.AppComponent
import com.yourself.searchyourcityweather.di.DaggerAppComponent

class ComponentInjector {

    companion object {
        lateinit var component: AppComponent
        fun init(context: Context) {
            component = DaggerAppComponent.builder()
                .movieRepositoryModule(MovieRepositoryModule())
                .build()

        }
    }
}