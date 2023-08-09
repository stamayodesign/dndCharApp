package com.example.dndcharapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CharStatsDao {
    @Insert
    suspend fun insert(stats:CharStats)

    @Update
    suspend fun update(stats:CharStats)

    @Delete
    suspend fun delete(stats: CharStats)

    @Query("SELECT * from character_stats_table WHERE charID = :key")
    fun get(key:Long):CharStats?

    @Query("DELETE FROM character_stats_table")
    fun clear()

    @Query("SELECT * FROM character_stats_table")
    fun getAll(): LiveData<List<CharStats>>

    @Query("SELECT * FROM character_stats_table WHERE charID IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<CharStats>
}