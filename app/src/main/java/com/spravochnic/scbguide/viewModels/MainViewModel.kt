package com.spravochnic.scbguide.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spravochnic.scbguide.NetworkEvent
import com.spravochnic.scbguide.State
import com.spravochnic.scbguide.api.ApiService
import com.spravochnic.scbguide.models.Categories
import com.spravochnic.scbguide.models.toCategories
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _categories = MutableLiveData<List<Categories>>()
    val categories: LiveData<List<Categories>> = _categories
    private val _categoriesResult = MutableLiveData<NetworkEvent<State>>()
    val categoriesResult : LiveData<NetworkEvent<State>> = _categoriesResult

    fun loadCategoriesLectory() {
        viewModelScope.launch {
            _categoriesResult.value = NetworkEvent(State.LOADING)
            try {
                val response = ApiService.API.getCategoriesLectory()
                if (response.result == "success") {
                    _categories.value = response.categories?.toCategories()
                    _categoriesResult.value = NetworkEvent(State.SUCCESS)
                } else {
                    _categories.value = listOf()
                    _categoriesResult.value = NetworkEvent(State.ERROR)
                }
            }catch (e: Exception){
                _categories.value = listOf()
                _categoriesResult.value = NetworkEvent(State.FAILURE)
            }
        }
    }
}