package com.tco_sol.pruebatecnica.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(tableName = "users", indices = [Index(value = ["user"], unique = true)])
data class User (
    @PrimaryKey
    @ColumnInfo(name = "user")
    var user: String,

    @ColumnInfo(name = "pwd")
    var pwd: String
)