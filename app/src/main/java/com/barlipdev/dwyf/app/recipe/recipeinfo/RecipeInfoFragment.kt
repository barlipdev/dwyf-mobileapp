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

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        preferences.matchedRecipeJson.observe(viewLifecycleOwner, Observer {
            if (it != null){
                Log.i("RecipeInfo","Works")
                viewModel.setMatchedRecipe(preferences.getMatchedRecipeFromJson().value!!)
            }
        })

    }

}