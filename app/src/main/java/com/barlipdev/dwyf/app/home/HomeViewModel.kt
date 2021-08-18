package com.barlipdev.dwyf.app.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.barlipdev.dwyf.network.responses.User

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigateToScan = MutableLiveData<Boolean>()
    val navigateToScan: LiveData<Boolean>
        get() = _navigateToScan

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun setUser(user: User?){
        _user.value = user!!
    }

    fun navigtateToScan(){
        _navigateToScan.value = true
    }

    fun navigateToScanFinished(){
        _navigateToScan.value = false
    }


    class Factory(val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
                @Suppress("UCHECKED_CAST")
                return HomeViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct Viewmodel")
        }

    }

}