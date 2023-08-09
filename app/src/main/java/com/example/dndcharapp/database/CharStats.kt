package com.example.dndcharapp.database;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_stats_table")
data class CharStats (
    @PrimaryKey(autoGenerate = true)
    var charID: Long = 0L,

    @ColumnInfo(name = "character_name")
    var cName:String = "",
    @ColumnInfo(name = "character_str")
    var cStr: Int = -1,
    @ColumnInfo(name = "character_dex")
    var cDex: Int = -1,
    @ColumnInfo(name = "character_con")
    var cCon: Int = -1,
    @ColumnInfo(name = "character_int")
    var cInt: Int = -1,
    @ColumnInfo(name = "character_wis")
    var cWis: Int = -1,
    @ColumnInfo(name = "character_cha")
    var cCha: Int = -1,
    @ColumnInfo(name = "character_level")
    var cLvl: Int = -1,
)
