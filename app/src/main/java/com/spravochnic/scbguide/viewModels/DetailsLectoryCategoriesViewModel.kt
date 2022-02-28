package com.spravochnic.scbguide.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spravochnic.scbguide.NetworkEvent
import com.spravochnic.scbguide.State
import com.spravochnic.scbguide.api.ApiService
import com.spravochnic.scbguide.models.DetailCategory
import com.spravochnic.scbguide.models.toDetailCategories
import kotlinx.coroutines.launch

class DetailsLectoryCategoriesViewModel: ViewModel() {

    private val _detailCategory = MutableLiveData<List<DetailCategory>>()
    val detailCategory: LiveData<List<DetailCategory>> = _detailCategory
    private val _detailCategoryResult = MutableLiveData<NetworkEvent<State>>()
    val detailCategoryResult: LiveData<NetworkEvent<State>> = _detailCategoryResult

    fun getDetailsLectoryCategories() {
        viewModelScope.launch {
            val response = ApiService.API.getDetailCategoriesLectory()
            try {
                if (response.result == "success"){
                    _detailCategory.value = response.categories.toDetailCategories()
                    _detailCategoryResult.value = NetworkEvent(State.SUCCESS)
                } else {
                    _detailCategory.value = listOf()
                    _detailCategoryResult.value = NetworkEvent(State.ERROR, response.error)
                }
            }catch (e: Exception) {
                _detailCategory.value = listOf()
                _detailCategoryResult.value = NetworkEvent(State.FAILURE, e.message)
            }
        }
    }
}