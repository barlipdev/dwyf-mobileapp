package com.barlipdev.dwyf.authentication.ui.login

import android.app.Application
import android.text.Editable
import androidx.lifecycle.*

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String>
        get() = _password

    private val _loginEvent = MutableLiveData<Boolean>()
    val loginEvent: LiveData<Boolean>
        get() = _loginEvent

    fun loginEvent(){
        _loginEvent.value = true;
    }

    fun loginEventFinished(){
        _loginEvent.value = false;
    }

    fun setEmail(s: Editable){
        _email.value = s.toString()
    }

    fun setPassword(s :Editable){
        _password.value = s.toString()
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