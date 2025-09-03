package com.example.jetpackproject.presentation.authorization

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.jetpackproject.data.local.JetpackDatabase
import com.example.jetpackproject.presentation.theme.JetpackProjectTheme
import com.example.jetpackproject.presentation.weather.WeatherActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var db: JetpackDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = applicationContext.getSharedPreferences("AUTH_PREFS", Context.MODE_PRIVATE)
        val isUserLoggedIn = sharedPref.getBoolean("isUserLoggedIn", false)

//        if (isUserLoggedIn) {
//            val intent = Intent(this, WeatherActivity::class.java)
//            startActivity(intent)
//            finish()
//        } else {
            db = Room.databaseBuilder(applicationContext, JetpackDatabase::class.java, "users_db")
                .build()
            setContent {
                JetpackProjectTheme {
                    Box(modifier = Modifier.fillMaxSize()) {
                        PagerScreen(db, this@MainActivity)
                    }
                }

        }
    }
}