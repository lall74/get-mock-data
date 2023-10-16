package com.tco_sol.pruebatecnica.ui

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.tco_sol.pruebatecnica.R
import com.tco_sol.pruebatecnica.data.PruebaTecnicaDatabase
import com.tco_sol.pruebatecnica.data.model.productJsonAdapter
import com.tco_sol.pruebatecnica.databinding.FragmentMainBinding
import com.tco_sol.pruebatecnica.ui.adapter.ProductsAdapter
import com.tco_sol.pruebatecnica.vm.MainViewModel
import com.tco_sol.pruebatecnica.vm.MainViewModelFactory


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
        var userName = ""

        _viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        val adapter = ProductsAdapter(ProductsAdapter.OnClickListener {
            val json = productJsonAdapter.toJson(it)
            view?.findNavController()?.navigate(MainFragmentDirections.actionMainFragmentToProductFragment(json))
        })

        // Parameters...
        if (!requireArguments().isEmpty) {
            userName = MainFragmentArgs.fromBundle(requireArguments()).name
        }

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
                viewModel.status.value = ""
                Session.currentUser?.let {
                    Utils.showLongToast(application, "Usuario registrado: $userName")

                    getMockData(application)
                }
            }
            else if (status != "") {
                Utils.showLongToast(application, "Error: $status")
            }
        })

        (activity as AppCompatActivity).supportActionBar?.title = "Menu Principal"
        (activity as AppCompatActivity).supportActionBar?.show()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun getMockData(application: Application) {
        try {
            viewModel.getMockData()
        } catch (e: Exception) {
            Utils.showToast(application, "Error de conexion: ${e.message}")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.mnuLogOutMainMenu -> {
                logOut()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        Session.endSession()
        view?.findNavController()?.navigate(R.id.action_global_logInFragment)
    }
}