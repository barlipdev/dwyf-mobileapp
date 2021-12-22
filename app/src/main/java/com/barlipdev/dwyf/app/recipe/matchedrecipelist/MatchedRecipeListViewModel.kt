package com.barlipdev.dwyf.app.recipe.matchedrecipelist

import android.app.Application
import androidx.lifecycle.*
import com.barlipdev.dwyf.network.responses.MatchedRecipe

class MatchedRecipeListViewModel(app: Application, list: List<MatchedRecipe>) : AndroidViewModel(app) {

    private val _matchedRecipeList = MutableLiveData<List<MatchedRecipe>>()
    val matchedRecipeList: LiveData<List<MatchedRecipe>>
        get() = _matchedRecipeList

    private val _navigationToDetails = MutableLiveData<MatchedRecipe?>()
    val navigationToDetails
        get() = _navigationToDetails

    init {
        setMatchedRecipeList(list)
    }

    fun setMatchedRecipeList(matchedRecipeList: List<MatchedRecipe>){
        _matchedRecipeList.value = matchedRecipeList
    }

    fun navigateToDetails(matchedRecipe: MatchedRecipe){
        _navigationToDetails.value = matchedRecipe
    }

    fun navigateToDetailsFinished(){
        _navigationToDetails.value = null
    }

    class Factory(val app: Application, val list: List<MatchedRecipe>): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MatchedRecipeListViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MatchedRecipeListViewModel(app,list) as T
            }
            throw IllegalArgumentException("Unbale to construct viewmodel")
        }

    }
}