package com.barlipdev.dwyf.app.recipe.filterrecipe

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.databinding.FilterRecipeFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.responses.FoodTypeFilter
import com.barlipdev.dwyf.network.responses.ProductFilter
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class FilterRecipeFragment : Fragment() {

    private lateinit var binding: FilterRecipeFragmentBinding
    private lateinit var preferences: DataStoreManager
    private val viewModel: FilterRecipeViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, FilterRecipeViewModel.Factory(activity.application)).get(FilterRecipeViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FilterRecipeFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        preferences = DataStoreManager(requireContext())
        viewModel.setFoodTypeFilter(FoodTypeFilter.SNIADANIE)
        viewModel.setProductFilter(ProductFilter.ALL)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.navigateToRecipe.observe(viewLifecycleOwner, Observer { isNavigate -> isNavigate?.let {
            if (this.findNavController().currentDestination?.id == R.id.filterRecipeFragment){
                if (isNavigate){
                    this.findNavController().navigate(R.id.action_filterRecipeFragment_to_recipeFragment)
                    viewModel.navigateToRecipeFinished()
                }
            }
        } })

        viewModel.matchedRecipe.observe(viewLifecycleOwner, Observer { matchedRecipe -> matchedRecipe?.let {
            when(it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        preferences.saveMatchedRecipe(it.value)
                    }
                    Log.i("RecipeInfo", it.value.recipe.name)
                    viewModel.navigateToRecipe()
                }
                is Resource.Failure -> {
                    Log.i("RecipeInfo",it.toString())
                }
            }
        } })

        preferences.userJson.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.user = preferences.getUserFromJson().value
            }
        })

    }

}