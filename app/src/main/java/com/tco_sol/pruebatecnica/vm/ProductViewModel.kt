package com.tco_sol.pruebatecnica.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tco_sol.pruebatecnica.data.dao.CommonDao
import com.tco_sol.pruebatecnica.data.model.Product
import kotlinx.coroutines.launch
import java.util.ArrayList

class ProductViewModel(private val database: CommonDao, application: Application) : AndroidViewModel(application) {

    val status = MutableLiveData<String>()
    val product = MutableLiveData<Product>()

    init {
        viewModelScope.launch {
            Log.i("Update", "ProductViewModel Init...")
        }
    }

    fun setProduct(product: Product?) {
        this.product.value = product
        Log.i("Update", "Product: $product")
    }

    fun getBattersList() : ArrayList<String> {
        val result = arrayListOf<String>()
        product.value?.let {
            it.batters?.let {
                it.batter?.let {
                    it.forEach { result.add(it.type ?: "") }
                }
            }
        }
        return result
    }

    fun getToppingsList() : ArrayList<String> {
        val result = arrayListOf<String>()
        product.value?.let {
            it.topping?.let {
                it.forEach { result.add(it.type ?: "") }
            }
        }
        return result
    }
}