package com.example.googletranslateapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.googletranslateapp.database.history.History
import com.example.googletranslateapp.database.history.HistoryDao

@Database(entities = [History::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract val historyDao: HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java,
                        "data_base"
                    )
                        .fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}