package com.barlipdev.dwyf.app.recipe.performingrecipe.performingrecipelist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.app.profile.ProfileFragmentDirections
import com.barlipdev.dwyf.app.profile.ShoppingListsAdapter
import com.barlipdev.dwyf.databinding.PerformingRecipesFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.utils.visible
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class PerformingRecipesFragment : Fragment() {

    private lateinit var binding: PerformingRecipesFragmentBinding
    private lateinit var preferences: DataStoreManager
    private val viewModel: PerformingRecipesViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, PerformingRecipesViewModel.Factory(activity.application)).get(
            PerformingRecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PerformingRecipesFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        preferences = DataStoreManager(requireContext())

        preferences.userJson.observe(viewLifecycleOwner, Observer {
            if (it != null){
                viewModel.getPerformingRecipes(preferences.getUserFromJson().value!!.id)
            }
        })

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.performingRecipesList.observe(viewLifecycleOwner, Observer { it ->
            when (it){

                is Resource.Success -> {
                    binding.progresBar.visible(false)
                    binding.performingRecipesListRecyclerView.adapter = PerformingRecipesAdapter(
                        PerformingRecipesAdapter.PerformingRecipesListener{
                            performingRecipe ->
                            run {
                                lifecycleScope.launch {
                                    preferences.savePerformingRecipe(performingRecipe)
                                }
                                viewModel.navigateToPerformedRecipe()
                            }

                    },it.value)
                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(),"Coś poszło nie tak", Toast.LENGTH_SHORT).show()
                }

            }
        })


        viewModel.navigateToPerformedRecipe.observe(viewLifecycleOwner, Observer { isNavigate -> isNavigate?.let {
            if (this.findNavController().currentDestination?.id == R.id.performingRecipesFragment){
                if (isNavigate){
                    this.findNavController().navigate(R.id.action_performingRecipesFragment_to_performingRecipeFragment)
                    viewModel.navigateToPerformedRecipeFinished()
                }
            }
        } })

    }

}