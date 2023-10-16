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
import com.tco_sol.pruebatecnica.databinding.FragmentLogInBinding
import com.tco_sol.pruebatecnica.vm.LogInViewModel
import com.tco_sol.pruebatecnica.vm.LogInViewModelFactory

class LogInFragment : Fragment() {

    private var _viewModel: LogInViewModel? = null
    private val viewModel get() = _viewModel!!

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_log_in, container, false)
        val application = requireNotNull(this.activity).application
        val database = PruebaTecnicaDatabase.getInstance(application).commonDao
        val viewModelFactory = LogInViewModelFactory(database, application)
        _viewModel = ViewModelProvider(this, viewModelFactory)[LogInViewModel::class.java]

        binding.lifecycleOwner = this.viewLifecycleOwner

        binding.btnLogIn.setOnClickListener() {
            logIn(application)
        }

        viewModel.status.observe(this.viewLifecycleOwner, Observer { status ->
            if (status == "SUCCESS") {
                view?.findNavController()?.navigate(LogInFragmentDirections.actionLogInFragmentToMainFragment(Session.currentUser!!.user))
            }
            else {
                Utils.showLongToast(application, status)
            }
        })

        setupSignUpLink()

        (activity as AppCompatActivity).supportActionBar?.title = "Iniciar Sesión"
        (activity as AppCompatActivity).supportActionBar?.hide()

        return binding.root
    }

    private fun logIn(application: Application) {
        val user = binding.txtUserFrmLogIn.text.toString().trim().lowercase()
        val pwd = binding.txtPwdFrmLogin.text.toString().trim()

        if (user == "") {
            Utils.showToast(application, "¡Ingresar usuario!")
            binding.txtUserFrmLogIn.requestFocus()
        }
        else if (pwd == "") {
            Utils.showToast(application, "¡Ingresar contraseña!")
            binding.txtPwdFrmLogin.requestFocus()
        }
        else {
            viewModel.signIn(user, pwd)
        }
    }

    private fun setupSignUpLink() {
        val signUpLink = binding.lblSignUp
        signUpLink.setTextColor(Color.BLUE)
        signUpLink.setOnClickListener {
            view?.findNavController()?.navigate(LogInFragmentDirections.actionLogInFragmentToSignUpFragment())
        }
    }
}