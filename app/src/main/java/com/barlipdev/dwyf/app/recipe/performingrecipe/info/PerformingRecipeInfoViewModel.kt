package com.barlipdev.dwyf.app.recipe.performingrecipe.info

import android.app.Application
import androidx.lifecycle.*
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.PerformingRecipeApi
import com.barlipdev.dwyf.network.RecipeApi
import com.barlipdev.dwyf.network.RemoteDataSource
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.repository.PerformingRecipeRepository
import com.barlipdev.dwyf.network.repository.RecipeRepository
import com.barlipdev.dwyf.network.responses.PerformingRecipe
import com.barlipdev.dwyf.network.responses.RecipeStatus
import com.barlipdev.dwyf.network.responses.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PerformingRecipeInfoViewModel(app: Application) : AndroidViewModel(app) {

    private val remoteDataSource = RemoteDataSource()
    private var preferences: DataStoreManager = DataStoreManager(app.applicationContext)
    private val authToken = runBlocking { preferences.authToken.first() }
    private val repository = PerformingRecipeRepository(remoteDataSource.buildApi(PerformingRecipeApi::class.java,authToken))

    private val _performingRecipe= MutableLiveData<PerformingRecipe>()
    val performingRecipe: LiveData<PerformingRecipe>
        get() = _performingRecipe

    private val _responsePerformingRecipe= MutableLiveData<Resource<PerformingRecipe>>()
    val responsePerformingRecipe: LiveData<Resource<PerformingRecipe>>
        get() = _responsePerformingRecipe

    private val _recipeStatus = MutableLiveData<String>()
    val recipeStatus: LiveData<String>
        get() = _recipeStatus

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun setUser(user: User){
        _user.value = user
    }

    fun setPerformingRecipe(performingRecipe: PerformingRecipe){
        _performingRecipe.value = performingRecipe
    }

    fun performRecipe(userId: String, performingRecipe: PerformingRecipe){
        viewModelScope.launch {
            _responsePerformingRecipe.value = repository.performRecipe(userId, performingRecipe)
        }
    }

    fun cancelPerformingRecipe(recipe: PerformingRecipe){
        recipe.recipeStatus = RecipeStatus.CANCELED
        viewModelScope.launch {
           _responsePerformingRecipe.value = repository.updatePerformRecipe(recipe)
        }
    }

    fun setRecipeStatus(recipeStatus: RecipeStatus){
        if (recipeStatus == RecipeStatus.IN_PROGRESS){
            _recipeStatus.value = "Status: W trakcie przygotowywania"
        }else if (recipeStatus == RecipeStatus.DONE){
            _recipeStatus.value = "Status: Zakończone"
        }else if (recipeStatus == RecipeStatus.CANCELED){
            _recipeStatus.value = "Status: Anulowane"
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PerformingRecipeInfoViewModel::class.java)){
                @Suppress("UCHECKED_CAST")
                return PerformingRecipeInfoViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct Viewmodel")
        }

    }
}