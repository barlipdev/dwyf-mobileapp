package com.barlipdev.dwyf.app.recipe

import android.app.Application
import androidx.lifecycle.*
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.network.RecipeApi
import com.barlipdev.dwyf.network.RemoteDataSource
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.repository.RecipeRepository
import com.barlipdev.dwyf.network.responses.MatchedRecipe
import com.barlipdev.dwyf.network.responses.User
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

//    private val remoteDataSource = RemoteDataSource()
//    private var preferences: DataStoreManager = DataStoreManager(application.applicationContext)
//    private val authToken = runBlocking { preferences.authToken.first() }
//    private val repository = RecipeRepository(remoteDataSource.buildApi(RecipeApi::class.java,authToken))
//
//    private val _matchedRecipe = MutableLiveData<Resource<MatchedRecipe>>()
//    val matchedRecipe: LiveData<Resource<MatchedRecipe>>
//        get() = _matchedRecipe
//
//    private val _user = MutableLiveData<User>()
//    val user: LiveData<User>
//        get() = _user
//
//    fun getPrefferedRecipe(userId: String){
//        viewModelScope.launch {
//            _matchedRecipe.value = repository.getPrefferedRecipe(userId)
//        }
//    }
//
//    fun setUser(user: User?){
//        _user.value = user!!
//    }

    class Factory(val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecipeViewModel::class.java)){
                @Suppress("UCHECKED_CAST")
                return RecipeViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct Viewmodel")
        }

    }

}