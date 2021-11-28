package com.barlipdev.dwyf.app.recipe.performingrecipe

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.barlipdev.dwyf.R
import com.barlipdev.dwyf.app.recipe.RecipeViewModel
import com.barlipdev.dwyf.app.recipe.viewpager.PerformingViewPagerAdapter
import com.barlipdev.dwyf.app.recipe.viewpager.ViewPagerAdapter
import com.barlipdev.dwyf.databinding.PerformingRecipeFragmentBinding
import com.barlipdev.dwyf.databinding.RecipeFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator

val pages = arrayOf(
    "Informacje",
    "Składniki"
)

class PerformingRecipeFragment : Fragment() {

    private lateinit var binding: PerformingRecipeFragmentBinding
    private val viewModel: PerformingRecipeViewModel by lazy {
        val activity = requireNotNull(this.activity){
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, PerformingRecipeViewModel.Factory(activity.application)).get(
            PerformingRecipeViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PerformingRecipeFragmentBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val viewPager = binding.viewpager
        val tabLayout = binding.tabLayout

        val adapter = fragmentManager?.let { PerformingViewPagerAdapter(it,lifecycle) }
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = com.barlipdev.dwyf.app.recipe.pages[position]
        }.attach()


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}