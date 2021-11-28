package com.barlipdev.dwyf.app.recipe.performingrecipe.ingredients

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
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.app.recipe.recipeingredients.RecipeIngredientsViewModel
import com.barlipdev.dwyf.app.recipe.recipeingredients.RecipeProductsAdapter
import com.barlipdev.dwyf.databinding.PerformingRecipeIngredientsFragmentBinding
import com.barlipdev.dwyf.databinding.RecipeIngredientsFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.Resource
import kotlinx.coroutines.launch

class PerformingRecipeIngredientsFragment : Fragment() {

    private lateinit var binding: PerformingRecipeIngredientsFragmentBinding
    private lateinit var preferences: DataStoreManager
    private val viewModel: PerformingRecipeIngredientsViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, PerformingRecipeIngredientsViewModel.Factory(activity.application)).get(
            PerformingRecipeIngredientsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PerformingRecipeIngredientsFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        preferences = DataStoreManager(requireContext())

        preferences.performingRecipeJson.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.recyclerViewProducts.adapter = RecipeProductsAdapter(preferences.getPerformingRecipeFromJson().value!!.matchedRecipe.recipe.productList)
                binding.recyclerViewProductsAvail.adapter = RecipeProductsAdapter(preferences.getPerformingRecipeFromJson().value!!.matchedRecipe.availableProducts)
            }
        })


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}