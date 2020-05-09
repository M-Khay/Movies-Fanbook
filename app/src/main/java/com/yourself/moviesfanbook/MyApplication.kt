package com.yourself.moviesfanbook

import android.app.Application
import com.yourself.moviesfanbook.di.ComponentInjector
import com.yourself.searchyourcityweather.utils.NetworkUtils

// appComponent lives in the Application class to share its lifecycle
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        ComponentInjector.init(applicationContext)

        NetworkUtils.registerNetworkCallback(this)

//        AppAnalytics.init(this)
//
//        MobileAds.initialize(this)

    }

}