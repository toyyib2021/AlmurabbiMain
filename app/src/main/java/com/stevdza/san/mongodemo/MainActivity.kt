package com.stevdza.san.mongodemo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.stevdza.san.mongodemo.navigation.Screen
import com.stevdza.san.mongodemo.navigation.SetupNavGraph
import com.stevdza.san.mongodemo.ui.theme.MongoDemoTheme
import io.realm.kotlin.mongodb.App

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MongoDemoTheme {
                MongoDemoTheme {
                    val navController = rememberNavController()
                    SetupNavGraph(
                        navController = navController
                    )
                }
            }
        }
    }
}

