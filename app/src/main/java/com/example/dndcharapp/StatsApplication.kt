package com.example.dndcharapp

import android.app.Application
import com.example.dndcharapp.database.CharStatsDatabase

class StatsApplication:Application(){
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database: CharStatsDatabase by lazy { CharStatsDatabase.getDatabase(this) }
}