package com.barlipdev.dwyf.authentication.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.authentication.ui.login.LoginViewModel
import com.barlipdev.dwyf.databinding.RegisterFragmentBinding
import com.barlipdev.dwyf.network.Resource

class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, RegisterViewModel.Factory(activity.application)).get(
            RegisterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = RegisterFragmentBinding.inflate(inflater,container,false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.user.observe(viewLifecycleOwner, Observer { user -> user?.let {
            when (it){
                is Resource.Success -> {
                    Log.i("UserInfo",it.toString())
                    Toast.makeText(context,"Udało się utworzyć konto!",Toast.LENGTH_SHORT).show()
                    viewModel.navigateToLogin()
                }
                is Resource.Failure -> {
                    Log.i("UserInfo",it.toString())
                    Toast.makeText(context,"Nie udało się utworzyć konta :/",Toast.LENGTH_SHORT).show()
                }
            }

        } })

        viewModel.navigateToLogin.observe(viewLifecycleOwner, Observer { isNavigate -> isNavigate?.let {
            if (this.findNavController().currentDestination?.id == R.id.registerFragment){
                if (isNavigate){
                    this.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    viewModel.navigateToLoginFinished()
                }
            }
        } })

        return binding.root
    }


}