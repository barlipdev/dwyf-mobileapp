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


    fun getUserFromJson(): LiveData<User?> {
        val gson: Gson = Gson()
        val _user = MutableLiveData<User>()
        _user.value = gson.fromJson(userJson.value, User::class.java)
        return _user
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


    companion object{
        private val KEY_AUTH = preferencesKey<String>("key_auth")
        private val KEY_USER = preferencesKey<String>("key_user")
    }
}