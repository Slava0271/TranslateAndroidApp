package com.example.googletranslateapp.historyfragment

import android.app.Application
import android.widget.Spinner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.googletranslateapp.database.history.HistoryDao
import com.example.googletranslateapp.translatefragment.TranslateViewModel
import com.google.cloud.translate.Translate

class HistoryFragmentFactory(
    private val application: Application,
    private val historyDao: HistoryDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(
                application , historyDao
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}