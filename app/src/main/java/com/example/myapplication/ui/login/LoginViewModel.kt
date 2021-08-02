package com.example.myapplication.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Network.Resource
import com.example.myapplication.Network.TokoBarangRepository
import com.example.myapplication.Network.response.LoginResponse
import com.example.myapplication.Network.response.RegisterResponse
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginViewModel (
    val repository: TokoBarangRepository
 ): ViewModel() {

     val loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
     val registerResponse: MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()

    fun fetchLogin(
        email: String,
        password: String
    ) = viewModelScope.launch {
        loginResponse.value = Resource.Loading()
        try {
            val response = repository.fetchLogin(email,password)
            loginResponse.value = Resource.Success(response.body()!!)
        } catch (e: Exception){

        }
    }
    fun fetchRegister(
        email: String,
        password: String
    ) = viewModelScope.launch {
        registerResponse.value = Resource.Loading()
        try {
            val response = repository.fetchRegister(email,password)
            registerResponse.value = Resource.Success(response.body()!!)
        } catch (e: Exception){
        }
    }

    fun savePreferenceToken(token: String){
        repository.savePreferencesToken(token)
    }


}