package com.example.mayank.myplaygame

import android.app.Application
import com.example.mayank.myplaygame.helpers.SharedPreferenceHelper
import com.example.mayank.myplaygame.network.ApiClient
import retrofit2.Retrofit

class PlayGameApplication : Application() {

    companion object {
        var sharedPrefs : SharedPreferenceHelper?= null

    }

    override fun onCreate() {
        super.onCreate()

        sharedPrefs = SharedPreferenceHelper()



    }
}