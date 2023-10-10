package com.example.trymeals

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.trymeals.adapter.MealsAdapter
import com.example.trymeals.databinding.ActivityMainBinding
import com.example.trymeals.util.NetworkResult
import com.example.trymeals.viewmodel.MealsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MealsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getMeals()
        val adapter = MealsAdapter()

        lifecycleScope.launch {
            viewModel.categories.observe(this@MainActivity){
                when(it){

                    is NetworkResult.Loading ->{
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is NetworkResult.Success -> {
                        binding.progressBar.visibility = View.GONE
                        adapter.differ.submitList(it.data?.categories)
                        binding.categoryRv.adapter = adapter
                    }

                    is NetworkResult.Error ->{
                        binding.progressBar.visibility = View.GONE
                        Log.e("MainActivity", "onCreate: ${it.message.toString()}" )
                    }
                    else -> Unit
                }
            }
        }
    }
}