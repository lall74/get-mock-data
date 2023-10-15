package com.tco_sol.pruebatecnica.vm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tco_sol.pruebatecnica.data.dao.CommonDao

class LogInViewModelFactory(
    private val database: CommonDao,
    private val application: Application
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogInViewModel::class.java)) {
            return LogInViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}