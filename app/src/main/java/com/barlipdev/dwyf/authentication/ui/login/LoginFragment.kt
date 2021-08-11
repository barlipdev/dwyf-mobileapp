package com.barlipdev.dwyf.authentication.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, LoginViewModel.Factory(activity.application)).get(
            LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = LoginFragmentBinding.inflate(inflater,container,false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.loginEvent.observe(viewLifecycleOwner, Observer { isLogin -> isLogin?.let {
            if (this.findNavController().currentDestination?.id == R.id.loginFragment){
                if (isLogin){
                    Log.i("Login",viewModel.email.value.toString() + " " + viewModel.password.value.toString())
                    //this.findNavController().navigate(R.id.action_mainAuthFragment_to_registerFragment)
                    viewModel.loginEventFinished()
                }
            }
        } })
        return binding.root

    }


}