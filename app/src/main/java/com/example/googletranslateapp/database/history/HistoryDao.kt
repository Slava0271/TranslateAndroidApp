package com.example.googletranslateapp.database.history

import androidx.room.*

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(history: History)

    @Update
    suspend fun update(history: History)

    @Query("Select * from history_table WHERE historyId = :key")
    suspend fun get(key: Long): History?

    @Query("DELETE FROM history_table")
    suspend fun clear()

    @Query("SELECT * FROM history_table ORDER BY historyId ASC")
    suspend fun getAllUsers(): List<History>


}