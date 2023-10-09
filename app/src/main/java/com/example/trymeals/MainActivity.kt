package com.example.trymeals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.trymeals.adapter.MealsAdapter
import com.example.trymeals.databinding.ActivityMainBinding
import com.example.trymeals.viewmodel.MealsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MealsViewModel>()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getMeals()
        val adapter = MealsAdapter()

        lifecycleScope.launch {
            viewModel.categories.collectLatest {
                adapter.submitList(it?.categories)
                binding.categoryRv.adapter = adapter
            }
        }
    }
}