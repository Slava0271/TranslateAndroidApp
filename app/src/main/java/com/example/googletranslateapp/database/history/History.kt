package com.example.googletranslateapp.database.history

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class History(
    @PrimaryKey(autoGenerate = true)
    var historyId: Long = 0L,

    @ColumnInfo
    var firstLanguage:String = "es" ,

    @ColumnInfo
    var secondLanguage:String = "es",

    @ColumnInfo
    var text:String = "",

    @ColumnInfo
    var translateResult:String =""
) {
}