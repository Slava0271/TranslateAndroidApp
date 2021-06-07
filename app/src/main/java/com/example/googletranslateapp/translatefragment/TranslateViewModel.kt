package com.example.googletranslateapp.translatefragment

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.example.googletranslateapp.database.history.History
import com.example.googletranslateapp.database.history.HistoryDao
import com.example.googletranslateapp.languages.Languages
import com.example.googletranslateapp.lifecycle.SingleLiveEvent
import com.google.cloud.translate.Translate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TranslateViewModel(
        application: Application, private val translate: Translate?,
        firstSpinner: Spinner, secondSpinner: Spinner, val historyDao: HistoryDao
) :
        AndroidViewModel(application) {
    val translateFragmentModel = TranslateFragmentModel()

    private val _navigate = SingleLiveEvent<NavDirections>()
    val navigate: LiveData<NavDirections> = _navigate

    private val _translateClickEvent = MutableLiveData<Boolean>()
    var language: String = "es"
    var secondLanguage: String = "es"


    fun navigate() {
        _navigate.postValue(TranslateFragmentDirections.actionTranslateFragmentToHistoryFragment())
    }

    fun clickTranslate() {
        _translateClickEvent.value = true
        translate()
        GlobalScope.launch(Dispatchers.Default) {
            insert(createHistory())
        }
    }

    init {
        listenSpinner(firstSpinner, true)
        listenSpinner(secondSpinner, false)
    }

    private fun createHistory(): History {
        val history = History()
        history.firstLanguage = language
        history.secondLanguage = secondLanguage
        history.text = translateFragmentModel.translateText
        history.translateResult = translateFragmentModel.afterTranslateText
        return history
    }

    private suspend fun insert(history: History) {
        historyDao.insert(history)
    }

    private fun translate() {
        val originalText: String = translateFragmentModel.translateText
        val translation = translate?.translate(
                originalText,
                Translate.TranslateOption.sourceLanguage(language),
                Translate.TranslateOption.targetLanguage(secondLanguage),
                Translate.TranslateOption.model("base")
        )
        Log.d(" text", "$originalText $translation")
        if (translation != null) {
            translateFragmentModel.afterTranslateText = translation.translatedText
        }
    }

    private fun listenSpinner(spinner: Spinner, isFirstSpinner: Boolean) {
        spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(
                            arg0: AdapterView<*>?, arg1: View,
                            position: Int, arg3: Long
                    ) {
                        if (isFirstSpinner) {
                            when (position) {
                                0 -> language = Languages.SPANISH.language
                                1 -> language = Languages.RUSSIAN.language
                                2 -> language = Languages.ENGLISH.language
                                3 -> language = Languages.FRENCH.language
                                4 -> language = Languages.GERMAN.language
                            }
                        } else {
                            when (position) {
                                0 -> secondLanguage = Languages.SPANISH.language
                                1 -> secondLanguage = Languages.RUSSIAN.language
                                2 -> secondLanguage = Languages.ENGLISH.language
                                3 -> secondLanguage = Languages.FRENCH.language
                                4 -> secondLanguage = Languages.GERMAN.language
                            }
                        }

                    }
                }
    }

}