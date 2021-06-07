package com.example.googletranslateapp.translatefragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.googletranslateapp.R
import com.example.googletranslateapp.database.DataBase
import com.example.googletranslateapp.databinding.FragmentTranslateBinding
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import kotlinx.android.synthetic.main.fragment_translate.*
import java.io.IOException


class TranslateFragment : Fragment() {
    private var translate: Translate? = null
    private lateinit var binding: FragmentTranslateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_translate, container, false
        )


        getTranslateService()
        val application = requireNotNull(this.activity).application
        val dataSourceHistory = DataBase.getInstance(application).historyDao

        val viewModelFactory = TranslateFragmentFactory(
            application, translate, binding.spinnerFrom, binding.spinnerTo, dataSourceHistory
        )
        val viewModel =
            ViewModelProvider(this, viewModelFactory).get(TranslateViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.navigate.observe(viewLifecycleOwner,::navigate)


        //   viewModel.translateClickEvent.observe(viewLifecycleOwner) {}
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createSpinner(spinnerFrom)
        createSpinner(spinnerTo)
    }

    private fun createSpinner(spinner: Spinner) {
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.languages,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun checkInternetConnection(): Boolean {
        val connectivityManager = this.requireContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        return activeNetwork?.isConnected == true
    }

    private fun getTranslateService() {

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        try {
            resources.openRawResource(R.raw.credentials).use { `is` ->
                val myCredentials = GoogleCredentials.fromStream(`is`)
                val translateOptions =
                    TranslateOptions.newBuilder().setCredentials(myCredentials).build()
                translate = translateOptions.service
            }
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }
    }

    private fun navigate(direction: NavDirections) {
        findNavController().navigate(direction)
    }
}