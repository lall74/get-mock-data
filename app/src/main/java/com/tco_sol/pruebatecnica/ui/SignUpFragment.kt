package com.tco_sol.pruebatecnica.ui

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.tco_sol.pruebatecnica.R
import com.tco_sol.pruebatecnica.data.PruebaTecnicaDatabase
import com.tco_sol.pruebatecnica.databinding.FragmentSignUpBinding
import com.tco_sol.pruebatecnica.vm.LogInViewModel
import com.tco_sol.pruebatecnica.vm.LogInViewModelFactory

class SignUpFragment : Fragment() {

    private var _viewModel: LogInViewModel? = null
    private val viewModel get() = _viewModel!!

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        val application = requireNotNull(this.activity).application
        val database = PruebaTecnicaDatabase.getInstance(application).commonDao
        val viewModelFactory = LogInViewModelFactory(database, application)
        _viewModel = ViewModelProvider(this, viewModelFactory)[LogInViewModel::class.java]

        binding.lifecycleOwner = this.viewLifecycleOwner

        binding.btnSignUpFrmSignUp.setOnClickListener() {
            signUp(application)
        }

        viewModel.status.observe(this.viewLifecycleOwner, Observer { status ->
            if (status == "SUCCESS") {
                view?.findNavController()?.navigate(SignUpFragmentDirections.actionSignUpFragmentToMainFragment())
            }
            else {
                Utils.showLongToast(application, status)
            }
        })

        (activity as AppCompatActivity).supportActionBar?.title = "Registrarse"
        (activity as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }

    private fun signUp(application: Application) {
        val user = binding.txtUserFrmSignUp.text.toString().trim().lowercase()
        val pwd = binding.txtPwdFrmSignUp.text.toString().trim()
        val confirmPwd = binding.txtConfirmPwdFrmSignUp.text.toString().trim()

        if (user == "") {
            Utils.showToast(application, "¡Ingresar usuario!")
            binding.txtUserFrmSignUp.requestFocus()
        }
        else if (pwd == "") {
            Utils.showToast(application, "¡Ingresar contraseña!")
            binding.txtPwdFrmSignUp.requestFocus()
        }
        else if (confirmPwd == "") {
            Utils.showToast(application, "¡Ingresar confirmación de contraseña!")
            binding.txtConfirmPwdFrmSignUp.requestFocus()
        }
        else if (pwd != confirmPwd) {
            Utils.showToast(application, "¡Contraseña y confirmacion no coinciden!")
            binding.txtConfirmPwdFrmSignUp.requestFocus()
        }
        else {
            viewModel.signUp(user, pwd)
        }
    }
}