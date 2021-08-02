package com.example.myapplication.ui.proccess

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Network.Resource
import com.example.myapplication.Network.TokoBarangRepository
import com.example.myapplication.Network.response.ItemsResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class AddEditViewModel (
    val repository: TokoBarangRepository
 ): ViewModel() {

    val addResponse: MutableLiveData<Resource<ItemsResponse>> = MutableLiveData()

    fun fetchAdd(
        kode_barang: String,
        nama_barang: String,
        jumlah_barang: String,
        harga_barang: String,
        satuan_barang: String,
        status_barang: String
    ) = viewModelScope.launch {
        addResponse.value = Resource.Loading()
        try {
            val response = repository.fetchAddBarang(
                repository.getPreferences()!!,
                kode_barang,
                nama_barang,
                jumlah_barang,
                harga_barang,
                satuan_barang,
                status_barang)
            addResponse.value = Resource.Success(response.body()!!)
        } catch (e: Exception){
            addResponse.value = Resource.Error(e.message.toString())
        }
    }

    fun fetchEdit(
        kode_barang: String,
        nama_barang: String,
        jumlah_barang: String,
        harga_barang: String,
        satuan_barang: String,
        status_barang: String
    ) = viewModelScope.launch {
        addResponse.value = Resource.Loading()
        try {
            val response = repository.fetchEditBarang(
                repository.getPreferences()!!,
                kode_barang,
                nama_barang,
                jumlah_barang,
                harga_barang,
                satuan_barang,
                status_barang)
            addResponse.value = Resource.Success(response.body()!!)
        } catch (e: Exception){
            addResponse.value = Resource.Error(e.message.toString())
        }
    }



}