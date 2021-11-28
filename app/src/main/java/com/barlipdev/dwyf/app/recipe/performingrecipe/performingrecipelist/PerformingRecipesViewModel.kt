package com.barlipdev.dwyf.app.recipe.performingrecipe.performingrecipelist

import android.app.Application
import androidx.lifecycle.*
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.PerformingRecipeApi
import com.barlipdev.dwyf.network.RemoteDataSource
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.repository.PerformingRecipeRepository
import com.barlipdev.dwyf.network.responses.PerformingRecipe
import com.barlipdev.dwyf.network.responses.ShoppingList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PerformingRecipesViewModel(application: Application) : AndroidViewModel(application) {

    private val remoteDataSource = RemoteDataSource()
    private var preferences: DataStoreManager = DataStoreManager(application.applicationContext)
    private val authToken = runBlocking { preferences.authToken.first() }
    private val repository = PerformingRecipeRepository(remoteDataSource.buildApi(PerformingRecipeApi::class.java,authToken))


    private val _performingRecipesList = MutableLiveData<Resource<List<PerformingRecipe>>>()
    val performingRecipesList: LiveData<Resource<List<PerformingRecipe>>>
        get() = _performingRecipesList

    private val _navigateToPerformedRecipe = MutableLiveData<Boolean>()
    val navigateToPerformedRecipe: LiveData<Boolean>
        get() = _navigateToPerformedRecipe

    fun getPerformingRecipes(userId: String){
        viewModelScope.launch {
            _performingRecipesList.value = repository.getPerformingRecipes(userId)
        }
    }

    fun navigateToPerformedRecipe(){
        _navigateToPerformedRecipe.value = true
    }

    fun navigateToPerformedRecipeFinished(){
        _navigateToPerformedRecipe.value = false
    }



    class Factory(val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerformingRecipesViewModel::class.java)){
                @Suppress("UCHECKED_CAST")
                return PerformingRecipesViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct Viewmodel")
        }

    }
}