package com.barlipdev.dwyf.app.product.scanning

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.barlipdev.dwyf.authentication.ui.login.LoginViewModel
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.AuthApi
import com.barlipdev.dwyf.network.RemoteDataSource
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.UserApi
import com.barlipdev.dwyf.network.repository.AuthRepository
import com.barlipdev.dwyf.network.repository.UserRepository
import com.barlipdev.dwyf.network.responses.Product
import com.barlipdev.dwyf.network.responses.ProductType
import com.barlipdev.dwyf.network.responses.UsefulnessState
import com.barlipdev.dwyf.network.responses.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.util.*

class ScanViewModel(application: Application) : AndroidViewModel(application) {

    private val _product = MutableLiveData<Resource<Product>>()
    val product: LiveData<Resource<Product>>
        get() = _product

    private val _addedProduct = MutableLiveData<Resource<Product>>()
    val addedProduct: LiveData<Resource<Product>>
        get() = _addedProduct


    private val remoteDataSource = RemoteDataSource()
    private var preferences: DataStoreManager = DataStoreManager(application.applicationContext)
    private val authToken = runBlocking { preferences.authToken.first() }
    private val repository = UserRepository(remoteDataSource.buildApi(UserApi::class.java,authToken))

    fun addProductByBarCode(
        barCode: String
    ){
        viewModelScope.launch {
                val product: Product = Product(barCode,"2021-04-04","",0.0,UsefulnessState.GOOD,ProductType.L,
                    emptyList<String>(),"")
                _product.value = repository.addProductByBarcode(product)
        }
    }

    fun addProduct(
        userId: String,
        product: Product
    ){
        viewModelScope.launch {
            _addedProduct.value = repository.addProduct(userId,product)
        }
    }


    class Factory(val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ScanViewModel::class.java)){
                @Suppress("UCHECKED_CAST")
                return ScanViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct Viewmodel")
        }

    }

}