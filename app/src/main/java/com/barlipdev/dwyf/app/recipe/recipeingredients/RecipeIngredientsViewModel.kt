package com.barlipdev.dwyf.app.recipe.recipeingredients

import android.app.Application
import androidx.lifecycle.*
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.RemoteDataSource
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.UserApi
import com.barlipdev.dwyf.network.repository.UserRepository
import com.barlipdev.dwyf.network.responses.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RecipeIngredientsViewModel(application: Application) : AndroidViewModel(application) {

    private val remoteDataSource = RemoteDataSource()
    private var preferences: DataStoreManager = DataStoreManager(application.applicationContext)
    private val authToken = runBlocking { preferences.authToken.first() }
    private val repository = UserRepository(remoteDataSource.buildApi(UserApi::class.java,authToken))

    private val _showProducts = MutableLiveData<Boolean>()
    val showProducts: LiveData<Boolean>
        get() = _showProducts

    private val _showProductsAvailable = MutableLiveData<Boolean>()
    val showProductsAvailable: LiveData<Boolean>
        get() = _showProductsAvailable

    private val _showProductsNotAvailable = MutableLiveData<Boolean>()
    val showProductsNotAvailable: LiveData<Boolean>
        get() = _showProductsNotAvailable

    private val _showButtonShoppingList = MutableLiveData<Boolean>()
    val showButtonShoppingList: LiveData<Boolean>
        get() = _showButtonShoppingList

    private val _userResponse = MutableLiveData<Resource<User>>()
    val userResponse: LiveData<Resource<User>>
        get() = _userResponse

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

    fun showButtonShoppingListOn(){
        _showButtonShoppingList.value = true;
    }

    fun showButtonShoppingListOff(){
        _showButtonShoppingList.value = false;
    }

    fun createShoppingList(userId: String, notAvailableProducts: List<Product>){
        viewModelScope.launch {
            _userResponse.value = repository.createShoppingList(userId,ShoppingList("",notAvailableProducts))
        }
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