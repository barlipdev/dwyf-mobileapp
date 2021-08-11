package com.barlipdev.dwyf.authentication.ui.mainauth

import android.app.Application
import androidx.lifecycle.*

class MainAuthViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigateToLogin = MutableLiveData<Boolean>();
    val navigateToLogin: LiveData<Boolean>
        get() = _navigateToLogin;

    private val _navigateToRegister = MutableLiveData<Boolean>();
    val navigateToRegister: LiveData<Boolean>
        get() = _navigateToRegister;

    fun navigateToLogin(){
        _navigateToLogin.value = true;
    }

    fun navigateToLoginFinished(){
        _navigateToLogin.value = false;
    }

    fun navigateToRegister(){
        _navigateToRegister.value = true;
    }

    fun navigateToRegisterFinished(){
        _navigateToRegister.value = false;
    }

    class Factory(val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainAuthViewModel::class.java)){
                @Suppress("UCHECKED_CAST")
                return MainAuthViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct Viewmodel")
        }

    }

}