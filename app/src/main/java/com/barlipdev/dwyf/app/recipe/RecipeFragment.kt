package com.barlipdev.dwyf.app.recipe

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.app.home.HomeViewModel
import com.barlipdev.dwyf.app.recipe.viewpager.ViewPagerAdapter
import com.barlipdev.dwyf.databinding.RecipeFragmentBinding
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.Resource
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import java.util.prefs.AbstractPreferences

val pages = arrayOf(
    "Informacje",
    "Składniki"
)

class RecipeFragment : Fragment() {

    private lateinit var binding: RecipeFragmentBinding
    //private lateinit var preferences: DataStoreManager
    private val viewModel: RecipeViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, RecipeViewModel.Factory(activity.application)).get(
            RecipeViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RecipeFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val viewPager = binding.viewpager
        val tabLayout = binding.tabLayout

        val adapter = fragmentManager?.let { ViewPagerAdapter(it,lifecycle) }
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = pages[position]
        }.attach()

//        preferences = DataStoreManager(requireContext())
//
//        preferences.userJson.observe(viewLifecycleOwner, Observer {
//            if (it != null){
//                viewModel.setUser(preferences.getUserFromJson().value)
//                viewModel.user.value?.let { it1 -> viewModel.getPrefferedRecipe(it1.id) }
//            }
//        })
//
//        viewModel.matchedRecipe.observe(viewLifecycleOwner, Observer { matchedRecipe -> matchedRecipe?.let {
//            when(it){
//                is Resource.Success -> {
//                    lifecycleScope.launch {
//                        preferences.saveMatchedRecipe(it.value)
//                        Log.i("RecipeInfo", it.value.recipe.name)
//                    }
//                }
//                is Resource.Failure -> {
//                    Log.i("RecipeInfo",it.toString())
//                }
//            }
//        } })


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}