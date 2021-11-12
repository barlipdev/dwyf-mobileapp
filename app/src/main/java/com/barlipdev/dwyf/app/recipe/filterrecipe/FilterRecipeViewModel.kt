package com.barlipdev.dwyf.app.recipe.filterrecipe

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.RecipeApi
import com.barlipdev.dwyf.network.RemoteDataSource
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.repository.RecipeRepository
import com.barlipdev.dwyf.network.responses.FoodTypeFilter
import com.barlipdev.dwyf.network.responses.MatchedRecipe
import com.barlipdev.dwyf.network.responses.ProductFilter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FilterRecipeViewModel(application: Application) :AndroidViewModel(application) {

    private val remoteDataSource = RemoteDataSource()
    private var preferences: DataStoreManager = DataStoreManager(application.applicationContext)
    private val authToken = runBlocking { preferences.authToken.first() }
    private val repository = RecipeRepository(remoteDataSource.buildApi(RecipeApi::class.java,authToken))

    private val _matchedRecipe = MutableLiveData<Resource<MatchedRecipe>>()
    val matchedRecipe: LiveData<Resource<MatchedRecipe>>
        get() = _matchedRecipe

    private val _navigateToRecipe = MutableLiveData<Boolean>()
    val navigateToRecipe: LiveData<Boolean>
        get() = _navigateToRecipe

    private val _productsFilter = MutableLiveData<ProductFilter>()
    val productsFilter: LiveData<ProductFilter>
        get() = _productsFilter

    private val _foodTypeFilter = MutableLiveData<FoodTypeFilter>()
    val foodTypeFilter: LiveData<FoodTypeFilter>
        get() = _foodTypeFilter

    fun getPrefferedRecipe(userId: String){
        viewModelScope.launch {
            _matchedRecipe.value = repository.getPrefferedRecipe(userId,productsFilter.value!!,foodTypeFilter.value!!)
        }
    }

    fun navigateToRecipe(){
        _navigateToRecipe.value = true
    }

    fun navigateToRecipeFinished(){
        _navigateToRecipe.value = false
    }

    fun setProductFilter(filter: ProductFilter){
        _productsFilter.value = filter
    }

    fun setFoodTypeFilter(filter: FoodTypeFilter){
        _foodTypeFilter.value = filter
    }


    class Factory(val app: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FilterRecipeViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return FilterRecipeViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct viewmodel")
        }

    }

}