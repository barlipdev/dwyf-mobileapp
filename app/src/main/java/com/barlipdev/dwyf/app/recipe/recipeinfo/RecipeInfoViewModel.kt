package com.barlipdev.dwyf.app.recipe.recipeinfo

import android.app.Application
import androidx.lifecycle.*
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.RecipeApi
import com.barlipdev.dwyf.network.RemoteDataSource
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.repository.RecipeRepository
import com.barlipdev.dwyf.network.responses.MatchedRecipe
import com.barlipdev.dwyf.network.responses.PerformingRecipe
import com.barlipdev.dwyf.network.responses.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RecipeInfoViewModel(application:  Application) : AndroidViewModel(application) {

    private val remoteDataSource = RemoteDataSource()
    private var preferences: DataStoreManager = DataStoreManager(application.applicationContext)
    private val authToken = runBlocking { preferences.authToken.first() }
    private val repository = RecipeRepository(remoteDataSource.buildApi(RecipeApi::class.java,authToken))

    private val _matchedRecipe = MutableLiveData<MatchedRecipe>()
    val matchedRecipe: LiveData<MatchedRecipe>
        get() = _matchedRecipe

    private val _performingRecipe= MutableLiveData<Resource<PerformingRecipe>>()
    val performingRecipe: LiveData<Resource<PerformingRecipe>>
        get() = _performingRecipe


    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _readyToCook = MutableLiveData<Boolean>()
    val readyToCook: LiveData<Boolean>
        get() = _readyToCook

    private val _navigateToPerforming = MutableLiveData<Boolean>()
    val navigateToPerforming: LiveData<Boolean>
        get() = _navigateToPerforming

    fun setMatchedRecipe(matchedRecipe: MatchedRecipe){
        _matchedRecipe.value = matchedRecipe!!
    }

    fun setUser(user: User){
        _user.value = user
    }

    fun setReadyToCook(){
        _readyToCook.value = true
    }

    fun setNotReadyToCook(){
        _readyToCook.value = false
    }

    fun navigateToPerformingOn(){
        _navigateToPerforming.value = true
    }

    fun navigateToPerformingOff(){
        _navigateToPerforming.value = false
    }

    fun addPerform(userId: String, matchedRecipe: MatchedRecipe){
        viewModelScope.launch {
            _performingRecipe.value = repository.addPerform(userId,matchedRecipe)
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecipeInfoViewModel::class.java)){
                @Suppress("UCHECKED_CAST")
                return RecipeInfoViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct Viewmodel")
        }

    }
}