package com.barlipdev.dwyf.app.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.RecipeApi
import com.barlipdev.dwyf.network.RemoteDataSource
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.repository.RecipeRepository
import com.barlipdev.dwyf.network.responses.MatchedRecipe
import com.barlipdev.dwyf.network.responses.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigateToScan = MutableLiveData<Boolean>()
    val navigateToScan: LiveData<Boolean>
        get() = _navigateToScan

    private val _matchedRecipe = MutableLiveData<Resource<MatchedRecipe>>()
    val matchedRecipe: LiveData<Resource<MatchedRecipe>>
        get() = _matchedRecipe

    private val _navigateToProducts = MutableLiveData<Boolean>()
    val navigateToProducts: LiveData<Boolean>
        get() = _navigateToProducts

    private val _navigateToFilterRecipe = MutableLiveData<Boolean>()
    val navigateToFilterRecipe: LiveData<Boolean>
        get() = _navigateToFilterRecipe

    private val _navigateToPerformingRecipes = MutableLiveData<Boolean>()
    val navigateToPerformingRecipes: LiveData<Boolean>
        get() = _navigateToPerformingRecipes



    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun setUser(user: User?){
        _user.value = user!!
    }

    fun navigtateToScan(){
        _navigateToScan.value = true
    }

    fun navigateToScanFinished(){
        _navigateToScan.value = false
    }

    fun navigtateToProducts(){
        _navigateToProducts.value = true
    }

    fun navigateToProductsFinished(){
        _navigateToProducts.value = false
    }

    fun navigateToFilterRecipe(){
        _navigateToFilterRecipe.value = true
    }

    fun navigateToFilterRecipeFinished(){
        _navigateToFilterRecipe.value = false
    }

    fun navigateToPerformingRecipes(){
        _navigateToPerformingRecipes.value = true
    }

    fun navigateToPerformingRecipesFinished(){
        _navigateToPerformingRecipes.value = false
    }


    class Factory(val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
                @Suppress("UCHECKED_CAST")
                return HomeViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct Viewmodel")
        }

    }

}