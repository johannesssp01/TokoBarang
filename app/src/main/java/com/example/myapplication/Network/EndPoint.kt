package com.example.myapplication.Network

import com.example.myapplication.Network.response.*
import retrofit2.Response
import retrofit2.http.*

interface EndPoint {
    @GET("items")
    suspend fun items(
        @Header("Authorization") token: String
    ): Response<List<ItemsResponse>>

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("email") origin: String,
        @Field("password") password: String
    ) : Response<RegisterResponse>

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") origin: String,
        @Field("password") password: String
    ) : Response<LoginResponse>

    @FormUrlEncoded
    @POST("item/add")
    suspend fun addBarang(
        @Header("Authorization") token: String,
        @Field("kode_barang") kode_barang: String,
        @Field("nama_barang") nama_barang: String,
        @Field("jumlah_barang") jumlah_barang: String,
        @Field("harga_barang") harga_barang: String,
        @Field("satuan_barang") satuan_barang: String,
        @Field("status_barang") status_barang: String,
    ) : Response<ItemsResponse>

    @FormUrlEncoded
    @POST("item/update")
    suspend fun updateBarang(
        @Header("Authorization") token: String,
        @Field("kode_barang") kode_barang: String,
        @Field("nama_barang") nama_barang: String,
        @Field("jumlah_barang") jumlah_barang: String,
        @Field("harga_barang") harga_barang: String,
        @Field("satuan_barang") satuan_barang: String,
        @Field("status_barang") status_barang: String,
    ) : Response<ItemsResponse>


    @FormUrlEncoded
    @POST("item/delete")
    suspend fun deleteBarang(
        @Header("Authorization") token: String,
        @Field("kode_barang") kode_barang: String,
    ) : Response<ItemsResponse>

    @FormUrlEncoded
    @POST("item/search")
    //@Headers("No-Authentication: true")
    suspend fun searchBarang(
        @Header("Authorization") token: String,
        @Field("kode_barang") kode_barang: String
    ) : Response<ItemsResponse>
}