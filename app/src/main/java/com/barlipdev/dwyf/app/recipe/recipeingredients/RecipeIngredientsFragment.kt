package com.barlipdev.dwyf.app.recipe.recipeingredients

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.barlipdev.dwyf.databinding.RecipeIngredientsFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager

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
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}