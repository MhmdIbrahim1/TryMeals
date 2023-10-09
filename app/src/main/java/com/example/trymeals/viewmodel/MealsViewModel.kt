package com.example.trymeals.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.CategoryResponse
import com.example.domain.usecase.GetMeals
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val getMealsUseCase: GetMeals
) : ViewModel(){

    private val  _categories : MutableStateFlow<CategoryResponse?> = MutableStateFlow(null)
    val categories = _categories.asStateFlow()


    fun getMeals(){
        viewModelScope.launch {
           try {
               _categories.value = getMealsUseCase()
           }catch (e: Exception){
               Log.e("MealsViewModel", "getMeals: ${e.message.toString()}")
           }
        }
    }

}