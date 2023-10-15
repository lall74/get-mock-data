package com.tco_sol.pruebatecnica.ui

import com.tco_sol.pruebatecnica.data.model.User

object Session {

    var logged: Boolean = false
    var currentUser: User? = null

    fun initSession(user: User) {
        this.currentUser = user
        this.logged = true
    }

    fun endSession() {
        this.currentUser = null
        this.logged = false
    }
}