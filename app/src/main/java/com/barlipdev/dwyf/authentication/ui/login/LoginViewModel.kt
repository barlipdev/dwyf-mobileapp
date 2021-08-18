package com.barlipdev.dwyf.authentication.ui.login

import android.app.Application
import android.content.Context
import android.text.Editable
import androidx.lifecycle.*
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.AuthApi
import com.barlipdev.dwyf.network.RemoteDataSource
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.repository.AuthRepository
import com.barlipdev.dwyf.network.responses.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigateToHome = MutableLiveData<Boolean>()
    val navigateToHome: LiveData<Boolean>
        get() = _navigateToHome

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>>
        get() = _user

    private val remoteDataSource = RemoteDataSource()
    private val repository = AuthRepository(remoteDataSource.buildApi(AuthApi::class.java))

    fun login(
        email: String,
        password: String
    ){
        _navigateToHome.value = true
        viewModelScope.launch {
            _user.value = repository.login(email,password)
        }
    }


    fun setEmail(s: Editable){
        _email.value = s.toString()
    }

    fun setPassword(s :Editable){
        _password.value = s.toString()
    }

    fun navigateToHome(){
        _navigateToHome.value = true
    }

    fun navigateToHomeFinished(){
        _navigateToHome.value = false
    }


    class Factory(val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
                @Suppress("UCHECKED_CAST")
                return LoginViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct Viewmodel")
        }

    }
}