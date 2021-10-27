package com.barlipdev.dwyf.app.recipe.recipeingredients

import android.app.Application
import androidx.lifecycle.*
import com.barlipdev.dwyf.network.responses.MatchedRecipe

class RecipeIngredientsViewModel(application: Application) : AndroidViewModel(application) {

    private val _showProducts = MutableLiveData<Boolean>()
    val showProducts: LiveData<Boolean>
        get() = _showProducts

    private val _showProductsAvailable = MutableLiveData<Boolean>()
    val showProductsAvailable: LiveData<Boolean>
        get() = _showProductsAvailable

    private val _showProductsNotAvailable = MutableLiveData<Boolean>()
    val showProductsNotAvailable: LiveData<Boolean>
        get() = _showProductsNotAvailable

    fun showProductsOn(){
        _showProducts.value = true
    }

    fun showProductsOff(){
        _showProducts.value = false
    }

    fun showAvailableProductsOn(){
        _showProductsAvailable.value = true
    }

    fun showAvailableProductsOff(){
        _showProductsAvailable.value = false
    }

    fun showNotAvailableProductsOn(){
        _showProductsNotAvailable.value = true
    }

    fun showNotAvailableProductsOff(){
        _showProductsNotAvailable.value = false
    }

    class Factory(val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecipeIngredientsViewModel::class.java)){
                @Suppress("UCHECKED_CAST")
                return RecipeIngredientsViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct Viewmodel")
        }

    }
}