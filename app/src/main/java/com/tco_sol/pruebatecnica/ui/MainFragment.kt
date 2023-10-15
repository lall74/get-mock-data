package com.tco_sol.pruebatecnica.ui

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.tco_sol.pruebatecnica.R
import com.tco_sol.pruebatecnica.data.PruebaTecnicaDatabase
import com.tco_sol.pruebatecnica.data.model.User
import com.tco_sol.pruebatecnica.databinding.FragmentMainBinding
import com.tco_sol.pruebatecnica.ui.adapter.ProductsAdapter
import com.tco_sol.pruebatecnica.vm.MainViewModel
import com.tco_sol.pruebatecnica.vm.MainViewModelFactory
import okhttp3.internal.Util

class MainFragment : Fragment() {

    private var _viewModel: MainViewModel? = null
    private val viewModel get() = _viewModel!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        val application = requireNotNull(this.activity).application
        val database = PruebaTecnicaDatabase.getInstance(application).commonDao
        val viewModelFactory = MainViewModelFactory(database, application)
        _viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val adapter = ProductsAdapter(ProductsAdapter.OnClickListener {

        })

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel
        binding.productsRvMainFragment.adapter = adapter

        viewModel.products.observe(this.viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                Log.i("Update", "MainFragment adapter.submitList...")
            }
        })

        viewModel.status.observe(this.viewLifecycleOwner, Observer { status ->

            // If no user is logged...
            if (!Session.logged) {
                view?.findNavController()?.navigate(MainFragmentDirections.actionMainFragmentToLogInFragment())
            }
            else if (status == "OK") {
                Session.currentUser?.let {
                    Utils.showLongToast(application, "Usuario registrado: ${it.user}")

                    getMockData(application)
                }
            }
            else {
                Utils.showLongToast(application, "Error: $status")
            }
        })

        (activity as AppCompatActivity).supportActionBar?.title = "Menu Principal"
        (activity as AppCompatActivity).supportActionBar?.show()

        return binding.root
    }

    private fun getMockData(application: Application) {
        try {
            viewModel.getMockData()

        } catch (e: Exception) {
            Utils.showToast(application, "Error de conexion: ${e.message}")
        }
    }
}