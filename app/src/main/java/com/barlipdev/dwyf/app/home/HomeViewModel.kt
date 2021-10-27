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

    private val remoteDataSource = RemoteDataSource()
    private var preferences: DataStoreManager = DataStoreManager(application.applicationContext)
    private val authToken = runBlocking { preferences.authToken.first() }
    private val repository = RecipeRepository(remoteDataSource.buildApi(RecipeApi::class.java,authToken))

    private val _navigateToScan = MutableLiveData<Boolean>()
    val navigateToScan: LiveData<Boolean>
        get() = _navigateToScan

    private val _matchedRecipe = MutableLiveData<Resource<MatchedRecipe>>()
    val matchedRecipe: LiveData<Resource<MatchedRecipe>>
        get() = _matchedRecipe

    private val _navigateToProducts = MutableLiveData<Boolean>()
    val navigateToProducts: LiveData<Boolean>
        get() = _navigateToProducts

    private val _navigateToRecipe = MutableLiveData<Boolean>()
    val navigateToRecipe: LiveData<Boolean>
        get() = _navigateToRecipe

    fun getPrefferedRecipe(userId: String){
        viewModelScope.launch {
            _matchedRecipe.value = repository.getPrefferedRecipe(userId)
        }
    }

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

    fun navigateToRecipe(){
        _navigateToRecipe.value = true
    }

    fun navigateToRecipeFinished(){
        _navigateToRecipe.value = false
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