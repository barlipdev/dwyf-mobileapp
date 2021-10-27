package com.barlipdev.dwyf.app.recipe.recipeinfo

import android.app.Application
import androidx.lifecycle.*
import com.barlipdev.dwyf.network.Resource
import com.barlipdev.dwyf.network.responses.MatchedRecipe
import com.barlipdev.dwyf.network.responses.User

class RecipeInfoViewModel(application:  Application) : AndroidViewModel(application) {

    private val _matchedRecipe = MutableLiveData<MatchedRecipe>()
    val matchedRecipe: LiveData<MatchedRecipe>
        get() = _matchedRecipe

    fun setMatchedRecipe(matchedRecipe: MatchedRecipe){
        _matchedRecipe.value = matchedRecipe!!
    }

    class Factory(val app: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecipeInfoViewModel::class.java)){
                @Suppress("UCHECKED_CAST")
                return RecipeInfoViewModel(app) as T
            }
            throw IllegalArgumentException("Unbale to construct Viewmodel")
        }

    }
}