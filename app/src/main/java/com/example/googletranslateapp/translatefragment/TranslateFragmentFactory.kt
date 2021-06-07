package com.example.googletranslateapp.translatefragment

import android.app.Application
import android.widget.Spinner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.googletranslateapp.database.history.HistoryDao
import com.google.cloud.translate.Translate

class TranslateFragmentFactory(
    private val application: Application,
    private val translate: Translate?,
    private val spinnerFirst: Spinner,
    private val spinnerSecond: Spinner,
    private val historyDao: HistoryDao
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TranslateViewModel::class.java)) {
            return TranslateViewModel(
                application, translate, spinnerFirst, spinnerSecond, historyDao
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}