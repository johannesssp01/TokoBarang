package com.example.myapplication.Network

import com.example.myapplication.Database.preferences.CekTokoPreference
import com.example.myapplication.Database.preferences.tokenOrigin

class TokoBarangRepository (
    private val api: EndPoint,
    private val pref: CekTokoPreference
        ){

    suspend fun fetchItems( token: String) = api.items(token)

    suspend fun fetchLogin(email: String,
                             password: String
            ) = api.login( email, password)

    suspend fun fetchRegister(email: String,
                           password: String
            ) = api.register( email, password)

    suspend fun fetchAddBarang(
        token: String,
         kode_barang: String,
         nama_barang: String,
         jumlah_barang: String,
         harga_barang: String,
         satuan_barang: String,
         status_barang: String
    ) = api.addBarang(
        token,
        kode_barang,
        nama_barang,
        jumlah_barang,
        harga_barang,
        satuan_barang,
        status_barang)

    suspend fun fetchEditBarang(
        token: String,
        kode_barang: String,
        nama_barang: String,
        jumlah_barang: String,
        harga_barang: String,
        satuan_barang: String,
        status_barang: String
    ) = api.updateBarang(
        token,
        kode_barang,
        nama_barang,
        jumlah_barang,
        harga_barang,
        satuan_barang,
        status_barang)


    suspend fun fetchDelete(
        token: String,
        kode_barang: String
    ) = api.deleteBarang( token,kode_barang)

    suspend fun fetchSearch(
        token: String,
        kode_barang: String
    ) = api.searchBarang( token,kode_barang)

    fun savePreferencesToken(token:String){
        pref.put(tokenOrigin, "Bearer $token" )
    }
    fun getPreferences(): String?{
        return pref.getString(tokenOrigin)

    }


}