package com.barlipdev.dwyf.app.recipe.performingrecipe.ingredients

import android.app.Application
import androidx.lifecycle.*

class PerformingRecipeIngredientsViewModel(application: Application) : AndroidViewModel(application) {

    class Factory(val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerformingRecipeIngredientsViewModel::class.java)){
                @Suppress("UCHECKED_CAST")
                return PerformingRecipeIngredientsViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct Viewmodel")
        }

    }
}