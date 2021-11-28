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