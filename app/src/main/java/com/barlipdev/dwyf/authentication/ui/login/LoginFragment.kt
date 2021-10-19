package com.barlipdev.dwyf.authentication.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.barlipdev.dwyf.app.home.HomeActivity
import com.barlipdev.dwyf.databinding.LoginFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.utils.startNewActivity
import com.barlipdev.dwyf.utils.visible
import kotlinx.coroutines.launch

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
        val preferences = DataStoreManager(requireContext())

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.progresBar.visible(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer { user -> user?.let {
            when (it){
                is Resource.Success -> {
                    binding.progresBar.visible(false)
                    Log.i("UserInfo",it.toString())
                    Toast.makeText(context,"Udało się zalogować!", Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        preferences.saveAuthToken(it.value.authToken)
                        preferences.saveUser(it.value.user)
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(context,"Nie udało się zalogować :/",Toast.LENGTH_SHORT).show()
                    Log.i("UserInfo",it.toString())
                }
            }

        } })

        viewModel.navigateToHome.observe(viewLifecycleOwner, Observer { isNavigate -> isNavigate?.let {
            binding.progresBar.visible(true)
        } })

        return binding.root

    }


}