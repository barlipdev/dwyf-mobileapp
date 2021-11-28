package com.barlipdev.dwyf.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.barlipdev.dwyf.network.responses.MatchedRecipe
import com.barlipdev.dwyf.network.responses.PerformingRecipe
import com.barlipdev.dwyf.network.responses.ShoppingList
import com.barlipdev.dwyf.network.responses.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map



class DataStoreManager(context: Context) {

    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(
            name = "dataStore"
        )
    }

    val authToken: Flow<String?>
    get() = dataStore.data.map { preferences ->
        preferences[KEY_AUTH]
    }

    val userJson: LiveData<String?> = dataStore.data.map { preferences ->
        preferences[KEY_USER]
    }.asLiveData()

    val matchedRecipeJson: LiveData<String?> = dataStore.data.map { preferences ->
        preferences[KEY_MATCHED_RECIPE]
    }.asLiveData()

    val performingRecipeJson: LiveData<String?> = dataStore.data.map { preferences ->
        preferences[KEY_PERFORMING_RECIPE]
    }.asLiveData()

    val currentShoppingListJson: LiveData<String?> = dataStore.data.map { preferences ->
        preferences[KEY_CURRENT_SHOPPINGLIST]
    }.asLiveData()

    fun getUserFromJson(): LiveData<User?> {
        val gson: Gson = Gson()
        val _user = MutableLiveData<User>()
        _user.value = gson.fromJson(userJson.value, User::class.java)
        return _user
    }

    fun getMatchedRecipeFromJson(): LiveData<MatchedRecipe?> {
        val gson: Gson = Gson()
        val _matchedRecipe = MutableLiveData<MatchedRecipe>()
        _matchedRecipe.value = gson.fromJson(matchedRecipeJson.value, MatchedRecipe::class.java)
        return _matchedRecipe
    }

    fun getPerformingRecipeFromJson(): LiveData<PerformingRecipe?> {
        val gson: Gson = Gson()
        val _performingRecipe = MutableLiveData<PerformingRecipe>()
        _performingRecipe.value = gson.fromJson(performingRecipeJson.value, PerformingRecipe::class.java)
        return _performingRecipe
    }

    fun getCurrentShoppingListFromJson(): LiveData<ShoppingList?> {
        val gson: Gson = Gson()
        val _currentShoppingList = MutableLiveData<ShoppingList>()
        _currentShoppingList.value = gson.fromJson(currentShoppingListJson.value, ShoppingList::class.java)
        return _currentShoppingList
    }

    suspend fun saveAuthToken(authToken: String){
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }

    }

    suspend fun saveUser(user: User){
        dataStore.edit { preferences ->
            val gson: Gson = Gson()
            val json: String = gson.toJson(user)
            preferences[KEY_USER] = json
        }
    }

    suspend fun saveMatchedRecipe(matchedRecipe: MatchedRecipe){
        dataStore.edit { preferences ->
            val gson: Gson = Gson()
            val json: String = gson.toJson(matchedRecipe)
            preferences[KEY_MATCHED_RECIPE] = json
        }
    }

    suspend fun savePerformingRecipe(performingRecipe: PerformingRecipe){
        dataStore.edit { preferences ->
            val gson: Gson = Gson()
            val json: String = gson.toJson(performingRecipe)
            preferences[KEY_PERFORMING_RECIPE] = json
        }
    }

    suspend fun saveCurrentShoppingList(shoppingList: ShoppingList){
        dataStore.edit { preferences ->
            val gson: Gson = Gson()
            val json: String = gson.toJson(shoppingList)
            preferences[KEY_CURRENT_SHOPPINGLIST] = json
        }
    }


    companion object{
        private val KEY_AUTH = preferencesKey<String>("key_auth")
        private val KEY_USER = preferencesKey<String>("key_user")
        private val KEY_MATCHED_RECIPE = preferencesKey<String>("key_matched_recipe")
        private val KEY_PERFORMING_RECIPE = preferencesKey<String>("key_performing_recipe")
        private val KEY_CURRENT_SHOPPINGLIST = preferencesKey<String>("key_current_shoppinglist")
    }
}