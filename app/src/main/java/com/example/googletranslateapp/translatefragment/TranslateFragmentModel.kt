package com.example.googletranslateapp.translatefragment

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TranslateFragmentModel(
    private var _translateText: String = "",
    private var _afterTranslateText: String = ""
) : BaseObservable(), Parcelable {

    @get:Bindable
    var translateText: String = _translateText
        set(value) {
            _translateText = value
            field = value
            notifyPropertyChanged(BR.translateText)
        }

    @get:Bindable
    var afterTranslateText: String = _afterTranslateText
        set(value) {
            _afterTranslateText = value
            field = value
            notifyPropertyChanged(BR.afterTranslateText)
        }

}