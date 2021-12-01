package com.barlipdev.dwyf.app.product.manual

import android.app.Application
import android.text.Editable
import androidx.lifecycle.*
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

class ManualProductAddingViewModel(app: Application) : AndroidViewModel(app) {

    private val _productName = MutableLiveData<String>()
    val productName: LiveData<String>
        get() = _productName

    private val _productCount = MutableLiveData<Double>()
    val productCount: LiveData<Double>
        get() = _productCount

    private val _productType = MutableLiveData<ProductType>()
    val productType: LiveData<ProductType>
        get() = _productType

    private val _expirationDate = MutableLiveData<String>()
    val expirationDate: LiveData<String>
        get() = _expirationDate

    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>>
        get() = _user

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val remoteDataSource = RemoteDataSource()
    private var preferences: DataStoreManager = DataStoreManager(app.applicationContext)
    private val authToken = runBlocking { preferences.authToken.first() }
    private val repository = UserRepository(remoteDataSource.buildApi(UserApi::class.java,authToken))

    fun setProductName(s: Editable){
        _productName.value = s.toString()
    }

    fun setProductCount(s: Editable){
        _productCount.value = s.toString().toDouble()
    }

    fun setProductType(type: ProductType){
        _productType.value = type
    }

    fun setExpirationDate(s: Editable){
        _expirationDate.value = s.toString()
    }

    fun addProduct(
        userId: String,
        productName: String,
        productCount: Double,
        productType: ProductType,
        expirationDate: String
    ) = viewModelScope.launch {

        if (productName.isEmpty()){
            _message.value = "Nazwa produktu nie może być pusta!"
        }else if (productCount.toString().isEmpty()){
            _message.value = "Ilość jest wymagana!"
        }else if(productType.toString().isEmpty() ){
            _message.value = "Typ wagi musi zostać wybrany!"
        }else if (expirationDate.isEmpty()){
            _message.value = "Data ważności musi zostać wybrana!"
        }else{
            val product = Product("",expirationDate,productName,productCount,UsefulnessState.GOOD,productType,
                emptyList(),"")

            _user.value = repository.addProductManualy(userId, product)
        }
    }

    class Factory(val app: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ManualProductAddingViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return ManualProductAddingViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct viewmodel")
        }

    }
}