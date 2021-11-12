package com.barlipdev.dwyf.app.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.barlipdev.dwyf.databinding.ProfileFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.utils.visible
import com.bumptech.glide.Glide

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding
    private lateinit var preferences: DataStoreManager
    private val viewModel: ProfileViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, ProfileViewModel.Factory(activity.application)).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ProfileFragmentBinding.inflate(inflater,container,false)
        preferences = DataStoreManager(requireContext())

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.progresBar.visible(true)

        viewModel.shoppingLists.observe(viewLifecycleOwner, Observer { it ->
            when (it){

                is Resource.Success -> {
                    binding.progresBar.visible(false)
                    binding.shoppingListsRecyclerView.adapter = ShoppingListsAdapter(it.value)
                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(),"Coś poszło nie tak", Toast.LENGTH_SHORT).show()
                }

            }
        })

        preferences.userJson.observe(viewLifecycleOwner, Observer {
            if (it != null){
                viewModel.getShoppingLists(preferences.getUserFromJson().value!!.id)
                Glide
                    .with(this)
                    .load(preferences.getUserFromJson().value!!.avatarUrl)
                    .into(binding.profImg)
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}