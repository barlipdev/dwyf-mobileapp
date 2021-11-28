package com.barlipdev.dwyf.app.profile.shoppinglist

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
import androidx.navigation.fragment.navArgs
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.app.home.HomeActivity
import com.barlipdev.dwyf.app.product.productlist.ProductAdapter
import com.barlipdev.dwyf.app.profile.ProfileFragmentDirections
import com.barlipdev.dwyf.databinding.ShoppingListFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.utils.startNewActivity
import com.barlipdev.dwyf.utils.visible
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class ShoppingListFragment : Fragment() {

    private lateinit var binding: ShoppingListFragmentBinding
    private lateinit var preferences: DataStoreManager
    private val viewModel: ShoppingListViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        val args: ShoppingListFragmentArgs by navArgs()
        ViewModelProvider(this, ShoppingListViewModel.Factory(activity.application,args.shoppingList)).get(
            ShoppingListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ShoppingListFragmentBinding.inflate(inflater,container,false)
        preferences = DataStoreManager(requireContext())

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.shoppingList.observe(viewLifecycleOwner, Observer { shoppingList -> shoppingList?.let {
            binding.shoppingListsRecyclerView.adapter = ShoppingListProductAdapter(shoppingList.productList)
        } })

        preferences.userJson.observe(viewLifecycleOwner, Observer {
            if (it != null){
                viewModel.setCurrentUser(preferences.getUserFromJson().value!!)
            }
        })

        viewModel.confirmationShow.observe(viewLifecycleOwner, Observer { confirmation -> confirmation?.let {
            if (confirmation){
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Usuń")
                    .setMessage("Czy napewno chcesz usunąć listę zakupów?")
                    .setNegativeButton("Nie") { dialog, which ->
                        viewModel.confirmationShowOff()
                    }
                    .setPositiveButton("Tak") { dialog, which ->
                        viewModel.removeShoppingList()
                        viewModel.confirmationShowOff()
                        Log.i("RemoveShopping","Works2")
                    }
                    .show()
            }
        } })

        viewModel.navigationToProfile.observe(viewLifecycleOwner, Observer { shoppingList -> shoppingList?.let {
            if (this.findNavController().currentDestination?.id == R.id.shoppingListFragment){
                if (shoppingList!=null){
                    this.findNavController().navigate(ShoppingListFragmentDirections.actionShoppingListFragmentToProfileFragment2())
                    viewModel.navigationToProfileOff()
                }
            }
        } })

        viewModel.user.observe(viewLifecycleOwner, Observer { user -> user?.let {
            when (it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        preferences.saveUser(it.value)
                    }
                    viewModel.navigationToProfileOn()
                }
                is Resource.Failure -> {
                    Log.i("RemoveShopping",it.toString())
                }
            }

        } })

    }

}