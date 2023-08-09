package com.example.dndcharapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CharStats::class], version = 1, exportSchema = false)
abstract class CharStatsDatabase:RoomDatabase() {
    abstract fun getCharStatsDao():CharStatsDao

    companion object{
        @Volatile
        private var INSTANCE: CharStatsDatabase? = null

        fun getDatabase(
            context:Context
        ): CharStatsDatabase {
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharStatsDatabase::class.java,
                    "character_stats_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}