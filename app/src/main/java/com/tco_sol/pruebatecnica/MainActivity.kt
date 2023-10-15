package com.tco_sol.pruebatecnica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.tco_sol.pruebatecnica.data.PruebaTecnicaDatabase
import com.tco_sol.pruebatecnica.databinding.ActivityMainBinding
import com.tco_sol.pruebatecnica.vm.MainViewModel
import com.tco_sol.pruebatecnica.vm.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private var _viewModel: MainViewModel? = null
    private val viewModel get () = _viewModel!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val navController = this.findNavController(R.id.myNavHostFragment)

        val application = requireNotNull(this).application
        val database = PruebaTecnicaDatabase.getInstance(application).commonDao

        val viewModelFactory = MainViewModelFactory(database, application)
        _viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        navController.setGraph(R.navigation.navigation)
        // NavigationUI.setupWithNavController(this, navController)

    }
}