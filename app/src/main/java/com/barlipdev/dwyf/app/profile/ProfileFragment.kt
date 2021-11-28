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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.app.home.HomeActivity
import com.barlipdev.dwyf.authentication.AuthenticationActivity
import com.barlipdev.dwyf.databinding.ProfileFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.utils.startNewActivity
import com.barlipdev.dwyf.utils.visible
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

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

        viewModel.shoppingLists.observe(viewLifecycleOwner, Observer { it ->
            when (it){

                is Resource.Success -> {
                    binding.progresBar.visible(false)
                    binding.shoppingListsRecyclerView.adapter = ShoppingListsAdapter(ShoppingListsAdapter.ShoppingListListener{
                            shoppingList ->
                            viewModel.navigateToShoppingListOn(shoppingList)
                    },it.value)
                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(),"Coś poszło nie tak", Toast.LENGTH_SHORT).show()
                }

            }
        })

        viewModel.navigateToShoppingList.observe(viewLifecycleOwner, Observer { shoppingList -> shoppingList?.let {
            if (this.findNavController().currentDestination?.id == R.id.profileFragment2){
                if (shoppingList!=null){
                    this.findNavController().navigate(ProfileFragmentDirections.actionProfileFragment2ToShoppingListFragment(shoppingList))
                    viewModel.navigateToShoppingListOff()
                }
            }
        } })

        viewModel.logout.observe(viewLifecycleOwner, Observer { isLogout -> isLogout?.let {
            if (isLogout){

                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Wyloguj")
                    .setMessage("Czy napewno chcesz się wylogować?")
                    .setCancelable(true)
                    .setNeutralButton("Anuluj") { dialog, which ->
                        viewModel.logoutFinished()
                    }
                    .setPositiveButton("Tak") { dialog, which ->
                        lifecycleScope.launch {
                            preferences.saveAuthToken("")
                            requireActivity().startNewActivity(AuthenticationActivity::class.java)
                        }
                        Toast.makeText(context,"Zostałeś wylogowany!", Toast.LENGTH_SHORT).show()
                        viewModel.logoutFinished()
                    }
                    .show()
            }

        } })

    }

}