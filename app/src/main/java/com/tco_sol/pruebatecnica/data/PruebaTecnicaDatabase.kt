package com.tco_sol.pruebatecnica.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tco_sol.pruebatecnica.data.dao.CommonDao
import com.tco_sol.pruebatecnica.data.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)

abstract class PruebaTecnicaDatabase : RoomDatabase() {
    abstract val commonDao: CommonDao

    companion object {
        @Volatile
        private var INSTANCE: PruebaTecnicaDatabase? = null

        fun getInstance(context: Context): PruebaTecnicaDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PruebaTecnicaDatabase::class.java,
                        "prueba_tecnica_database",
                    ).fallbackToDestructiveMigration()
                        .addMigrations()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}