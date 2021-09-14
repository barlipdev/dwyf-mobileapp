package com.barlipdev.dwyf.app.product.productlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.RemoteDataSource
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.UserApi
import com.barlipdev.dwyf.network.repository.UserRepository
import com.barlipdev.dwyf.network.responses.Product
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProductsViewModel(application: Application) : AndroidViewModel(application) {

    private val _productsList = MutableLiveData<Resource<List<Product>>>()
    val productsList: LiveData<Resource<List<Product>>>
        get() = _productsList

    private val remoteDataSource = RemoteDataSource()
    private var preferences: DataStoreManager = DataStoreManager(application.applicationContext)
    private val authToken = runBlocking { preferences.authToken.first() }
    private val repository = UserRepository(remoteDataSource.buildApi(UserApi::class.java,authToken))

    fun getProductsList(
        userId: String
    ){
        viewModelScope.launch {
            _productsList.value = repository.getProductList(userId)
        }
    }


    class Factory(val app: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductsViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return ProductsViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct viewmodel")
        }

    }

}