package com.barlipdev.dwyf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.barlipdev.dwyf.app.home.HomeActivity
import com.barlipdev.dwyf.authentication.AuthenticationActivity
import com.barlipdev.dwyf.datastore.DataStoreManager
import com.barlipdev.dwyf.utils.startNewActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences = DataStoreManager(this)

        preferences.authToken.asLiveData().observe(this, Observer {
            val activity = if (it == null) AuthenticationActivity::class.java else HomeActivity::class.java
            startNewActivity(activity)
        })



    }

}