package com.barlipdev.dwyf.app.recipe.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.barlipdev.dwyf.app.recipe.recipeinfo.RecipeInfoFragment
import com.barlipdev.dwyf.app.recipe.recipeingredients.RecipeIngredientsFragment

private const val NUM_TABS = 2;

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS;
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return RecipeInfoFragment()
            1 -> return RecipeIngredientsFragment()
        }
        return RecipeInfoFragment()
    }

}