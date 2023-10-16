package com.tco_sol.pruebatecnica.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.tco_sol.pruebatecnica.R
import com.tco_sol.pruebatecnica.data.PruebaTecnicaDatabase
import com.tco_sol.pruebatecnica.data.model.productJsonAdapter
import com.tco_sol.pruebatecnica.databinding.FragmentProductBinding
import com.tco_sol.pruebatecnica.ui.fragments.ProductDetailFragment
import com.tco_sol.pruebatecnica.ui.fragments.ProductHeaderFragment
import com.tco_sol.pruebatecnica.vm.ProductViewModel
import com.tco_sol.pruebatecnica.vm.ProductViewModelFactory

class ProductFragment : Fragment() {

    private var _viewModel: ProductViewModel? = null
    private val viewModel get() = _viewModel!!

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        val application = requireNotNull(this.activity).application
        val database = PruebaTecnicaDatabase.getInstance(application).commonDao
        val viewModelFactory = ProductViewModelFactory(database, application)
        _viewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        // Parameters...
        if (!requireArguments().isEmpty) {
            val json = ProductFragmentArgs.fromBundle(requireArguments()).product
            viewModel.setProduct(productJsonAdapter.fromJson(json))

            lateinit var fragmentHeader: Fragment
            lateinit var fragmentDetail: Fragment

            fragmentHeader = ProductHeaderFragment()
            fragmentHeader.arguments = Bundle().apply {
                putString("PRODUCT", json)
            }

            fragmentDetail = ProductDetailFragment()
            fragmentDetail.arguments = Bundle().apply {
                putString("PRODUCT", json)
            }

            // Header and detail fragment...
            childFragmentManager.beginTransaction()
                .replace(R.id.fcvProductHeaderFragmentProduct, fragmentHeader)
                .replace(R.id.fcvProductDetailFragmentProduct, fragmentDetail)
                .commit()

            Log.i("Update", "Product json: $json")
        }

        (activity as AppCompatActivity).supportActionBar?.title = "Producto"

        return binding.root
    }
}