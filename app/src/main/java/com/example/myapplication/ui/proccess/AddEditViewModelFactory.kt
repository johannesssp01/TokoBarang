package com.example.myapplication.ui.proccess

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.Network.TokoBarangRepository

class AddEditViewModelFactory  (
    private val repository: TokoBarangRepository

): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddEditViewModel(repository) as T
    }
}