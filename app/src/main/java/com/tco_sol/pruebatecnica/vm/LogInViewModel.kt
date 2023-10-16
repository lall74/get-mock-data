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

    fun validateUser(txtUser: String) = runCatching {
        require(txtUser.length >= 8) { "!Usuario no menor a 8 caracteres!" }
    }

    fun validatePwd(txtPwd: String) = runCatching {
        require(txtPwd.length >= 6) { "!La contraseña debe tener al menos 6 caracteres!" }
        require(txtPwd.any { it.isUpperCase() }) { "!La contrasela debe incluir al menos una letra mayúscula!" }
    }

    fun signIn(txtUser: String, txtPwd: String) {

        viewModelScope.launch {
            val result = try {
                val user = database.getUser(txtUser)
                if (user == null) {
                    "USER ERROR|Usuario inválido"
                } else {
                    if (user.pwd != txtPwd) {
                        "PWD ERROR|Contraseña incorrecta"
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

        // Complexity and validations...
        validateUser(txtUser).onFailure {
            status.value = it.message
            return
        }

        validatePwd(txtPwd).onFailure {
            status.value = it.message
            return
        }

        viewModelScope.launch {
            val result = try {
                var user = database.getUser(txtUser)
                if (user == null) {
                    user = User(txtUser, txtPwd)
                    database.insertUser(user)
                    Session.initSession(user)
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