package com.barlipdev.dwyf.app.recipe.performingrecipe.info

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
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
import com.barlipdev.dwyf.app.recipe.performingrecipe.performingrecipelist.PerformingRecipesAdapter
import com.barlipdev.dwyf.app.recipe.recipeinfo.RecipeInfoViewModel
import com.barlipdev.dwyf.databinding.PerformingRecipeInfoFragmentBinding
import com.barlipdev.dwyf.databinding.RecipeInfoFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.responses.RecipeStatus
import com.barlipdev.dwyf.network.responses.UsefulnessState
import com.barlipdev.dwyf.utils.visible
import kotlinx.coroutines.launch

class PerformingRecipeInfoFragment : Fragment() {

    private lateinit var binding: PerformingRecipeInfoFragmentBinding
    private lateinit var preferences: DataStoreManager
    private val viewModel: PerformingRecipeInfoViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, PerformingRecipeInfoViewModel .Factory(activity.application)).get(
            PerformingRecipeInfoViewModel ::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PerformingRecipeInfoFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        preferences = DataStoreManager(requireContext())

        binding.description.setMovementMethod(ScrollingMovementMethod())

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences.performingRecipeJson.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.performingRecipe = preferences.getPerformingRecipeFromJson().value!!
                viewModel.setRecipeStatus(preferences.getPerformingRecipeFromJson().value!!.recipeStatus)
                viewModel.setPerformingRecipe(preferences.getPerformingRecipeFromJson().value!!)

                if (preferences.getPerformingRecipeFromJson().value!!.recipeStatus == RecipeStatus.IN_PROGRESS){
                    binding.status.setTextColor(Color.parseColor("#EEC84B"))
                }else if (preferences.getPerformingRecipeFromJson().value!!.recipeStatus == RecipeStatus.DONE){
                    binding.status.setTextColor(Color.parseColor("#6bfc03"))
                }else if (preferences.getPerformingRecipeFromJson().value!!.recipeStatus == RecipeStatus.CANCELED){
                    binding.status.setTextColor(Color.parseColor("#fc0303"))
                }
                binding.button2.isEnabled = preferences.getPerformingRecipeFromJson().value!!.recipeStatus == RecipeStatus.IN_PROGRESS
                binding.button3.isEnabled = preferences.getPerformingRecipeFromJson().value!!.recipeStatus == RecipeStatus.IN_PROGRESS
            }
        })

        preferences.userJson.observe(viewLifecycleOwner, Observer {
            if (it != null){
                binding.user = preferences.getUserFromJson().value!!
                viewModel.setUser(preferences.getUserFromJson().value!!)
            }
        })

        viewModel.responsePerformingRecipe.observe(viewLifecycleOwner, Observer { it ->
            when (it){

                is Resource.Success -> {
                    lifecycleScope.launch {
                        preferences.savePerformingRecipe(it.value)
                    }

                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(),"Coś poszło nie tak", Toast.LENGTH_SHORT).show()
                }

            }
        })

    }

}