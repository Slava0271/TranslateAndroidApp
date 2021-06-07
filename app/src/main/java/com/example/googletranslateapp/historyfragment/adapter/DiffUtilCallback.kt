package com.example.googletranslateapp.historyfragment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.googletranslateapp.database.history.History

class DiffUtilCallback
    : DiffUtil.ItemCallback<History>() {
    override fun areItemsTheSame(
        oldItem: History,
        newItem: History
    ): Boolean {
        return oldItem.historyId == newItem.historyId
    }

    override fun areContentsTheSame(
        oldItem: History,
        newItem: History
    ): Boolean {
        return oldItem == newItem
    }
}