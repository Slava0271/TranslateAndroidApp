package com.example.googletranslateapp.historyfragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.googletranslateapp.database.history.History
import com.example.googletranslateapp.database.history.HistoryDao
import com.example.googletranslateapp.lifecycle.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application, val historyDao: HistoryDao) : AndroidViewModel(application) {
    private val _navigate = SingleLiveEvent<NavDirections>()
    val navigate: LiveData<NavDirections> = _navigate

    private val _users = MutableLiveData<List<History>>()
    val users = _users

    private val _clear = MutableLiveData<NavDirections>()
    val clear: LiveData<NavDirections> = _clear

    fun navigateBack() {
        _navigate.postValue(HistoryFragmentDirections.actionHistoryFragmentToTranslateFragment())
    }

    fun clearList() {
        viewModelScope.launch {
            clear()
        }
        _clear.postValue(HistoryFragmentDirections.actionHistoryFragmentSelf())
    }

    suspend fun clear() {
        historyDao.clear()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _users.postValue(historyDao.getAllUsers())
        }
    }

}