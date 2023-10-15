package com.tco_sol.pruebatecnica.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tco_sol.pruebatecnica.data.dao.CommonDao
import com.tco_sol.pruebatecnica.data.model.Product
import com.tco_sol.pruebatecnica.services.PruebaTecnicaApi
import kotlinx.coroutines.launch

class MainViewModel(private val database: CommonDao, application: Application) : AndroidViewModel(application) {

    val products = MutableLiveData<List<Product>>()
    val status = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            status.value = "OK"
            Log.i("Update", "MainViewModel Init...")
        }
    }

    fun getMockData() {
        viewModelScope.launch {
            Log.i("Update", "getMockData Init...")
            try {
                val response = PruebaTecnicaApi.retrofitService.getMockData()
                Log.i("Update", "getMockData Response: $response")
                response.forEach { product ->
                    Log.i("Update", "Product: $product")
                }
                products.value = response
            }
            catch (e: Exception) {
                Log.i("Update", "getMockData Error: ${e.message}")
            }
        }
    }

}