package com.tco_sol.pruebatecnica.vm

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tco_sol.pruebatecnica.data.dao.CommonDao
import com.tco_sol.pruebatecnica.data.model.User
import com.tco_sol.pruebatecnica.ui.Session
import kotlinx.coroutines.launch

class LogInViewModel (private val database: CommonDao, application: Application) : AndroidViewModel(application) {

    val status = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            Log.i("Update", "LogInViewModel Init...")
        }
    }

    fun signIn(txtUser: String, txtPwd: String) {
        viewModelScope.launch {
            var result = ""
            result = try {
                val user = database.getUser(txtUser)
                if (user == null) {
                    "¡Usuario inválido!"
                } else {
                    if (user.pwd != txtPwd) {
                        "¡Contraseña inválida!"
                    } else {
                        Session.initSession(user)
                        "SUCCESS"
                    }
                }
            } catch (e: Exception) {
                e.message.toString()
            }
            status.value = result
        }
    }

    fun signUp(txtUser: String, txtPwd: String) {
        viewModelScope.launch {
            var result = ""
            result = try {
                var user = database.getUser(txtUser)
                if (user == null) {
                    user = User(txtUser, txtPwd)
                    database.insertUser(user)
                    "SUCCESS"
                } else {
                    "¡Usuario ya registrado!"
                }
            } catch (e: Exception) {
                e.message.toString()
            }
            status.value = result
        }
    }
}