package com.barlipdev.dwyf.authentication.ui.mainauth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.databinding.MainAuthFragmentBinding

class MainAuthFragment : Fragment() {

    private val viewModel: MainAuthViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this,MainAuthViewModel.Factory(activity.application)).get(MainAuthViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MainAuthFragmentBinding.inflate(inflater,container,false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.navigateToLogin.observe(viewLifecycleOwner, Observer { isNavigate -> isNavigate?.let {
            if (this.findNavController().currentDestination?.id == R.id.mainAuthFragment){
                if (isNavigate){
                    this.findNavController().navigate(R.id.action_mainAuthFragment_to_loginFragment)
                    viewModel.navigateToLoginFinished()
                }
            }
        } })

        viewModel.navigateToRegister.observe(viewLifecycleOwner, Observer { isNavigate -> isNavigate?.let {
            if (this.findNavController().currentDestination?.id == R.id.mainAuthFragment){
                if (isNavigate){
                    this.findNavController().navigate(R.id.action_mainAuthFragment_to_registerFragment)
                    viewModel.navigateToRegisterFinished()
                }
            }
        } })
        return binding.root
    }


}