package com.example.googletranslateapp.historyfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.googletranslateapp.R
import com.example.googletranslateapp.database.DataBase
import com.example.googletranslateapp.databinding.FragmentHistoryBinding
import com.example.googletranslateapp.databinding.FragmentTranslateBinding
import com.example.googletranslateapp.historyfragment.adapter.HistoryAdapter
import com.example.googletranslateapp.translatefragment.TranslateFragmentFactory
import com.example.googletranslateapp.translatefragment.TranslateViewModel

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val historyAdapter = HistoryAdapter {
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_history, container, false
        )

        val application = requireNotNull(this.activity).application

        val dataSourceHistory = DataBase.getInstance(application).historyDao

        val viewModelFactory = HistoryFragmentFactory(application, dataSourceHistory)

        val viewModel =
                ViewModelProvider(this, viewModelFactory).get(HistoryViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.adapter = historyAdapter
        viewModel.users.observe(viewLifecycleOwner) { historyAdapter.submitList(it) }
        viewModel.navigate.observe(viewLifecycleOwner, ::navigate)
        viewModel.clear.observe(viewLifecycleOwner, ::navigate)

        return binding.root

    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }

}