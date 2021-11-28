package com.barlipdev.dwyf.app.recipe.recipeinfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.barlipdev.dwyf.databinding.RecipeInfoFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager
import android.text.method.ScrollingMovementMethod
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.network.Resource
import kotlinx.coroutines.launch

class RecipeInfoFragment : Fragment() {

    private lateinit var binding: RecipeInfoFragmentBinding
    private lateinit var preferences: DataStoreManager
    private val viewModel: RecipeInfoViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, RecipeInfoViewModel.Factory(activity.application)).get(
            RecipeInfoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeInfoFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        preferences = DataStoreManager(requireContext())

        binding.description.setMovementMethod(ScrollingMovementMethod())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences.matchedRecipeJson.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.matchedRecipe = preferences.getMatchedRecipeFromJson().value!!
                viewModel.setMatchedRecipe(preferences.getMatchedRecipeFromJson().value!!)
                if (preferences.getMatchedRecipeFromJson().value!!.notAvailableProducts.isNotEmpty()){
                    viewModel.setNotReadyToCook()
                }else{
                    viewModel.setReadyToCook()
                }
            }
        })

        preferences.userJson.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.user = preferences.getUserFromJson().value!!
                viewModel.setUser(preferences.getUserFromJson().value!!)
            }
        })

        viewModel.performingRecipe.observe(viewLifecycleOwner, Observer { recipe -> recipe?.let {
            when (it){
                is Resource.Success -> {
                    lifecycleScope.launch {
                        preferences.savePerformingRecipe(it.value)
                    }
                    viewModel.navigateToPerformingOn()
                }
                is Resource.Failure -> {
                    Log.i("PerformingRecipe",it.toString())
                }
            }

        } })

        viewModel.navigateToPerforming.observe(viewLifecycleOwner, Observer { isNavigate -> isNavigate?.let {
            if (this.findNavController().currentDestination?.id == R.id.recipeFragment){
                if (isNavigate){
                    this.findNavController().navigate(R.id.action_recipeFragment_to_performingRecipeFragment)
                    viewModel.navigateToPerformingOff()
                }
            }
        } })

    }

}