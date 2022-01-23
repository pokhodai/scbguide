package com.spravochnic.scbguide

import android.app.Application

class App : Application() {

    companion object {

        private var reference: App? = null

        fun getInstance(): App {
            return reference ?: throw IllegalStateException("App is not initialized.")
        }
    }

    override fun onCreate() {
        super.onCreate()
        reference = this
    }
}