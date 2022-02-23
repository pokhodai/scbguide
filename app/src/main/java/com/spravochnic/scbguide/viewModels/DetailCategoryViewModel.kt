package com.spravochnic.scbguide.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spravochnic.scbguide.*
import com.spravochnic.scbguide.api.ApiService
import com.spravochnic.scbguide.models.Categories
import com.spravochnic.scbguide.models.DetailCategory
import com.spravochnic.scbguide.models.toDetailCategories
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailCategoryViewModel: ViewModel() {

    private val _detail = MutableLiveData<List<DetailCategory>>()
    val detail: LiveData<List<DetailCategory>> = _detail
    private val _detailResult = MutableLiveData<NetworkEvent<State>>()
    val detailResult : LiveData<NetworkEvent<State>> = _detailResult

    fun loadDetailsCategoriesData() {
        viewModelScope.launch {
            _detailResult.value = NetworkEvent(State.LOADING)
            try {
                val response = ApiService.API.getDetailCategoriesLectory()
                if (response.result == "success") {
                    _detail.value = response.detailsCategories?.toDetailCategories()
                    _detailResult.value = NetworkEvent(State.SUCCESS)
                }else{
                    _detail.value = listOf()
                    _detailResult.value = NetworkEvent(State.ERROR, response.error)
                }
            } catch (e: Exception) {
                _detail.value = listOf()
                _detailResult.value = NetworkEvent(State.FAILURE, e.message)
            }
        }
    }
}