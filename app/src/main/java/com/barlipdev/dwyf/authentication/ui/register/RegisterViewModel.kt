package com.barlipdev.dwyf.authentication.ui.register

import android.app.Application
import android.text.Editable
import androidx.lifecycle.*
import com.barlipdev.dwyf.authentication.ui.login.LoginViewModel
import com.barlipdev.dwyf.network.AuthApi
import com.barlipdev.dwyf.network.RemoteDataSource
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.repository.AuthRepository
import com.barlipdev.dwyf.network.responses.User
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin

    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

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


    fun register(
        username: String,
        email: String,
        password: String
    ) = viewModelScope.launch {
        _user.value = repository.register(username,email,password)
    }

    fun setUsername(s: Editable){
        _username.value = s.toString()
    }

    fun setEmail(s: Editable){
        _email.value = s.toString()
    }

    fun setPassword(s : Editable){
        _password.value = s.toString()
    }

    fun navigateToLogin(){
        _navigateToLogin.value = true
    }

    fun navigateToLoginFinished(){
        _navigateToLogin.value = false
    }

    class Factory(val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)){
                @Suppress("UCHECKED_CAST")
                return RegisterViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct Viewmodel")
        }

    }
}