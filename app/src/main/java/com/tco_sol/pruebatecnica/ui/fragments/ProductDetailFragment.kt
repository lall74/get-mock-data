package com.tco_sol.pruebatecnica.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.tco_sol.pruebatecnica.R
import com.tco_sol.pruebatecnica.data.PruebaTecnicaDatabase
import com.tco_sol.pruebatecnica.data.model.productJsonAdapter
import com.tco_sol.pruebatecnica.databinding.FragmentProductDetailBinding
import com.tco_sol.pruebatecnica.databinding.FragmentProductHeaderBinding
import com.tco_sol.pruebatecnica.vm.ProductViewModel
import com.tco_sol.pruebatecnica.vm.ProductViewModelFactory

class ProductDetailFragment : Fragment() {

    private var _viewModel: ProductViewModel? = null
    private val viewModel get() = _viewModel!!

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
        val application = requireNotNull(this.activity).application
        val database = PruebaTecnicaDatabase.getInstance(application).commonDao
        val viewModelFactory = ProductViewModelFactory(database, application)
        val battersArrayAdapter : ArrayAdapter<*>
        val toppingsArrayAdapter : ArrayAdapter<*>

        _viewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel


        arguments?.takeIf { it.containsKey("PRODUCT") }?.apply {
            val json = getString("PRODUCT")
            viewModel.setProduct(productJsonAdapter.fromJson(json))

            battersArrayAdapter = ArrayAdapter(
                application,
                android.R.layout.simple_list_item_1,
                viewModel.getBattersList()
            )
            binding.lvBattersProductDetailFragment.adapter = battersArrayAdapter

            toppingsArrayAdapter = ArrayAdapter(
                application,
                android.R.layout.simple_list_item_1,
                viewModel.getToppingsList()
            )
            binding.lvTopingsProductDetailFragment.adapter = toppingsArrayAdapter

        }

        return binding.root
    }
}