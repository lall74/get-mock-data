package com.tco_sol.pruebatecnica.services

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tco_sol.pruebatecnica.data.model.Product
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://mocki.io/v1/"

private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(5, TimeUnit.MINUTES)
    .writeTimeout(10, TimeUnit.MINUTES)
    .build()

private val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface PruebaTecnicaApiService {
    @GET("eeced007-6b29-4c9d-ab63-c115a990d927")
    suspend fun getMockData(): List<Product>
}

object PruebaTecnicaApi {
    val retrofitService: PruebaTecnicaApiService by lazy {
        retrofit.create(PruebaTecnicaApiService::class.java)
    }
}