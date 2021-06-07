package com.example.googletranslateapp.historyfragment.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.googletranslateapp.database.history.History
import com.example.googletranslateapp.databinding.HistoryItemBinding

class HistoryViewHolder(private val binding: HistoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: History, listener: ((History) -> Unit)?) {
        binding.user = item
        binding.userContainer.setOnClickListener { listener?.invoke(item) }
    }
}
