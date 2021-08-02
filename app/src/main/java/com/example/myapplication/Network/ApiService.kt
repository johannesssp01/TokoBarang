package com.example.myapplication.Network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val baseUrl = "https://hoodwink.medkomtek.net/api/"
object ApiService {

    fun getClient(): EndPoint {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                var request = chain.request()
                    request.newBuilder().build()
                chain.proceed(request)
            }
            .build()

        val gson = GsonBuilder().serializeNulls().create()

        return Retrofit.Builder()
            .baseUrl( baseUrl )
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(EndPoint::class.java)
    }

}