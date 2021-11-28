package com.barlipdev.dwyf.app.profile.shoppinglist

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.RemoteDataSource
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.UserApi
import com.barlipdev.dwyf.network.repository.UserRepository
import com.barlipdev.dwyf.network.responses.ShoppingList
import com.barlipdev.dwyf.network.responses.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ShoppingListViewModel(application: Application, list: ShoppingList) : AndroidViewModel(application) {

    private val _shoppingList = MutableLiveData<ShoppingList>()
    val shoppingList: LiveData<ShoppingList>
        get() = _shoppingList

    private val _confirmationShow = MutableLiveData<Boolean>()
    val confirmationShow: LiveData<Boolean>
        get() = _confirmationShow

    private val _navigationToProfile = MutableLiveData<Boolean>()
    val navigationToProfile: LiveData<Boolean>
        get() = _navigationToProfile

    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>>
        get() = _user

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User>
        get() = _currentUser

    private val remoteDataSource = RemoteDataSource()
    private var preferences: DataStoreManager = DataStoreManager(application.applicationContext)
    private val authToken = runBlocking { preferences.authToken.first() }
    private val repository = UserRepository(remoteDataSource.buildApi(UserApi::class.java,authToken))

    init {
        setShoppingList(list)
    }

    fun setShoppingList(shoppingList: ShoppingList){
        _shoppingList.value = shoppingList
    }

    fun setCurrentUser(user: User){
        _currentUser.value = user
    }

    fun confirmationShowOn(){
        _confirmationShow.value = true
    }

    fun confirmationShowOff(){
        _confirmationShow.value = false
    }

    fun navigationToProfileOn(){
        _navigationToProfile.value = true
    }

    fun navigationToProfileOff(){
        _navigationToProfile.value = false
    }

    fun removeShoppingList(){
        Log.i("RemoveShopping",currentUser.value!!.id)
        Log.i("RemoveShopping",shoppingList.value!!.name)
        viewModelScope.launch {
            _user.value = repository.deleteShoppingList(currentUser.value!!.id,shoppingList.value!!)
        }
    }

    class Factory(val app: Application, val list: ShoppingList): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ShoppingListViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return ShoppingListViewModel(app,list) as T
            }
            throw IllegalArgumentException("Unbale to construct viewmodel")
        }

    }
}