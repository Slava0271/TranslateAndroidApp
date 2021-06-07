package com.example.googletranslateapp.historyfragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.googletranslateapp.database.history.History
import com.example.googletranslateapp.databinding.HistoryItemBinding

class HistoryAdapter(private val listener: ((History) -> Unit)? = null) :
    ListAdapter<History, HistoryViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding =
            HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

}