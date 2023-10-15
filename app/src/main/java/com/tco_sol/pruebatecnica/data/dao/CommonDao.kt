package com.tco_sol.pruebatecnica.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tco_sol.pruebatecnica.data.model.User

@Dao
interface CommonDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE user = :user")
    suspend fun getUser(user: String) : User?

}