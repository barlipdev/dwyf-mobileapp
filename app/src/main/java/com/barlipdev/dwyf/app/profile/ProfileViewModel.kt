package com.barlipdev.dwyf.app.profile

import android.app.Application
import androidx.lifecycle.*
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.RemoteDataSource
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.UserApi
import com.barlipdev.dwyf.network.repository.UserRepository
import com.barlipdev.dwyf.network.responses.ShoppingList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val _shoppingLists = MutableLiveData<Resource<List<ShoppingList>>>()
    val shoppingLists: LiveData<Resource<List<ShoppingList>>>
        get() = _shoppingLists

    private val _navigateToShoppingList = MutableLiveData<ShoppingList?>()
    val navigateToShoppingList
        get() = _navigateToShoppingList

    private val _logout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean>
        get() = _logout

    private val remoteDataSource = RemoteDataSource()
    private var preferences: DataStoreManager = DataStoreManager(application.applicationContext)
    private val authToken = runBlocking { preferences.authToken.first() }
    private val repository = UserRepository(remoteDataSource.buildApi(UserApi::class.java,authToken))

    fun getShoppingLists(userId: String){
        viewModelScope.launch {
            _shoppingLists.value = repository.getShoppingLists(userId)
        }
    }

    fun navigateToShoppingListOn(shoppingList: ShoppingList){
        _navigateToShoppingList.value = shoppingList
    }

    fun navigateToShoppingListOff(){
        _navigateToShoppingList.value = null
    }

    fun logout(){
        _logout.value = true
    }

    fun logoutFinished(){
        _logout.value = false
    }

    class Factory(val app: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return ProfileViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct viewmodel")
        }

    }

}