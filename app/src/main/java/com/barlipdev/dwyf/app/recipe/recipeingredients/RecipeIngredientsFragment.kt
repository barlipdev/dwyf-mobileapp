package com.barlipdev.dwyf.app.recipe.recipeingredients

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
import com.barlipdev.dwyf.app.profile.shoppinglist.ShoppingListFragmentDirections
import com.barlipdev.dwyf.app.recipe.RecipeFragmentDirections
import com.barlipdev.dwyf.databinding.RecipeIngredientsFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.utils.startNewActivity
import com.barlipdev.dwyf.utils.visible
import kotlinx.coroutines.launch

class RecipeIngredientsFragment : Fragment() {

    private lateinit var binding: RecipeIngredientsFragmentBinding
    private lateinit var preferences: DataStoreManager
    private val viewModel: RecipeIngredientsViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, RecipeIngredientsViewModel.Factory(activity.application)).get(
            RecipeIngredientsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeIngredientsFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        preferences = DataStoreManager(requireContext())

        preferences.matchedRecipeJson.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.recyclerViewProducts.adapter = RecipeProductsAdapter(preferences.getMatchedRecipeFromJson().value!!.recipe.productList)
                binding.recyclerViewProductsAvail.adapter = RecipeProductsAdapter(preferences.getMatchedRecipeFromJson().value!!.availableProducts)
                binding.recyclerViewProductsNotAvail.adapter = RecipeProductsAdapter(preferences.getMatchedRecipeFromJson().value!!.notAvailableProducts)
                binding.matchedRecipe = preferences.getMatchedRecipeFromJson().value

                if (preferences.getMatchedRecipeFromJson().value!!.notAvailableProducts.isNotEmpty()){
                    viewModel.showButtonShoppingListOn()
                    viewModel.showNotAvailableProductsOn()
                }else{
                    viewModel.showButtonShoppingListOff()
                    viewModel.showNotAvailableProductsOff()
                }
            }
        })


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.userResponse.observe(viewLifecycleOwner, Observer { user -> user?.let {
            when (it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        preferences.saveUser(it.value)
                    }
                    Toast.makeText(context,"Dodano nowa listę zakupów!",Toast.LENGTH_SHORT)
                    viewModel.navigateToProfileOn()
                }
                is Resource.Failure -> {
                    Toast.makeText(context,"Nie udało się dodać listy zakupów!",Toast.LENGTH_SHORT)
                    Log.i("UserShoppingList",it.toString())
                }
            }

        } })

        preferences.userJson.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.user = preferences.getUserFromJson().value
            }
        })

        viewModel.navigateToProfile.observe(viewLifecycleOwner, Observer { isNavigate -> isNavigate?.let {
            if (this.findNavController().currentDestination?.id == R.id.recipeFragment){
                if (isNavigate){
                    this.findNavController().navigate(RecipeFragmentDirections.actionRecipeFragmentToProfileFragment2())
                    viewModel.navigateToProfileOff()
                }
            }
        } })


    }

}