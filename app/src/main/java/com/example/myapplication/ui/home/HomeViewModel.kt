package com.example.myapplication.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Network.Resource
import com.example.myapplication.Network.TokoBarangRepository
import com.example.myapplication.Network.response.ItemsResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(
    val repository: TokoBarangRepository
): ViewModel(){

    val prefrences: MutableLiveData<String?> = MutableLiveData()
    val itemsResponse: MutableLiveData<Resource<List<ItemsResponse>>> = MutableLiveData()
    val searchResponse: MutableLiveData<Resource<ItemsResponse>> = MutableLiveData()

    fun getPreferences(){
        prefrences.value = repository.getPreferences()
    }
    init {
        fetchItem()
    }

    fun fetchItem() = viewModelScope.launch {
        //itemsResponse.value = Resource.Loading()
        try {
            val response = repository.fetchItems(repository.getPreferences().toString())
            itemsResponse.value = Resource.Success(response.body()!!)
        } catch (e: Exception){
            itemsResponse.value = Resource.Error(e.message.toString())
        }
    }

    fun fetchSearch(
        kode_barang: String
    ) = viewModelScope.launch {
        searchResponse.value = Resource.Loading()
        try {
            val response = repository.fetchSearch(repository.getPreferences().toString(),kode_barang)
            searchResponse.value = Resource.Success(response.body()!!)
        } catch (e: Exception){
            searchResponse.value = Resource.Error(e.message.toString())
        }
    }

    fun fetchDelete(
        kode_barang: String
    ) = viewModelScope.launch {
        //itemsResponse.value = Resource.Loading()
        try {
            repository.fetchDelete(repository.getPreferences().toString(),kode_barang)
            //itemsResponse.value = Resource.Success(response.body()!!)
        } catch (e: Exception){
            //itemsResponse.value = Resource.Error(e.message.toString())
        }
    }

}