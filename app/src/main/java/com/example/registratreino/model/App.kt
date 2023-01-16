package com.example.registratreino.model

import android.app.Application
import com.example.registratreino.model.AppDatabase

class App: Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }
}