package com.tco_sol.pruebatecnica.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.tco_sol.pruebatecnica.R
import com.tco_sol.pruebatecnica.data.PruebaTecnicaDatabase
import com.tco_sol.pruebatecnica.data.model.productJsonAdapter
import com.tco_sol.pruebatecnica.databinding.FragmentProductBinding
import com.tco_sol.pruebatecnica.databinding.FragmentProductHeaderBinding
import com.tco_sol.pruebatecnica.vm.ProductViewModel
import com.tco_sol.pruebatecnica.vm.ProductViewModelFactory

class ProductHeaderFragment : Fragment() {

    private var _viewModel: ProductViewModel? = null
    private val viewModel get() = _viewModel!!

    private var _binding: FragmentProductHeaderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_header, container, false)
        val application = requireNotNull(this.activity).application
        val database = PruebaTecnicaDatabase.getInstance(application).commonDao
        val viewModelFactory = ProductViewModelFactory(database, application)
        _viewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        arguments?.takeIf { it.containsKey("PRODUCT") }?.apply {
            val json = getString("PRODUCT")
            viewModel.setProduct(productJsonAdapter.fromJson(json))
        }

        return binding.root
    }
}