package com.barlipdev.dwyf.app.recipe.matchedrecipelist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.app.profile.shoppinglist.ShoppingListProductAdapter
import com.barlipdev.dwyf.app.recipe.filterrecipe.FilterRecipeFragmentDirections
import com.barlipdev.dwyf.databinding.MatchedRecipeListFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager
import kotlinx.coroutines.launch

class MatchedRecipeListFragment : Fragment() {

    private lateinit var binding: MatchedRecipeListFragmentBinding
    private lateinit var preferences: DataStoreManager
    private val viewModel: MatchedRecipeListViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        val args: MatchedRecipeListFragmentArgs by navArgs()
        ViewModelProvider(this, MatchedRecipeListViewModel.Factory(activity.application,args.matchedRecipeList.toList())).get(
            MatchedRecipeListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MatchedRecipeListFragmentBinding.inflate(inflater,container,false)

        preferences = DataStoreManager(requireContext())
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.matchedRecipeList.observe(viewLifecycleOwner, Observer { matchedRecipeList -> matchedRecipeList?.let {
            binding.matchedRecipeListRecyclerView.adapter = MatchedRecipeListAdapter(
                MatchedRecipeListAdapter.MatchedRecipeListListener {
                matchedRecipe ->
                    run {
                        lifecycleScope.launch {
                            preferences.saveMatchedRecipe(matchedRecipe)
                        }
                        viewModel.navigateToDetails(matchedRecipe)
                    }
            },matchedRecipeList)
        } })

        viewModel.navigationToDetails.observe(viewLifecycleOwner, Observer { isNavigate -> isNavigate?.let {
            if (this.findNavController().currentDestination?.id == R.id.matchedRecipeListFragment){
                if (isNavigate != null){
                    this.findNavController().navigate(MatchedRecipeListFragmentDirections.actionMatchedRecipeListFragmentToRecipeFragment())
                    viewModel.navigateToDetailsFinished()
                }
            }
        } })

    }

}