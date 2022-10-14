package com.example.scarlettest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.scarlettest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var serviceLocator: ScarletServiceLocator
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels(
        factoryProducer = { MainViewModelFactory(serviceLocator.chattingService) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        serviceLocator = ScarletServiceLocator(application)

        binding.vm = viewModel
        binding.lifecycleOwner = this
    }
}