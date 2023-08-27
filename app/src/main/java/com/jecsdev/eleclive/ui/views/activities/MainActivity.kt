package com.jecsdev.eleclive.ui.views.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jecsdev.eleclive.ui.navigation.Screens
import com.jecsdev.eleclive.ui.theme.ElecliveTheme
import com.jecsdev.eleclive.ui.viewModels.MainViewModel
import com.jecsdev.eleclive.ui.views.components.CameraPreview
import com.jecsdev.eleclive.ui.views.screens.ElectionsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElecliveTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Prepare navController for navigation
                    val navController = rememberNavController()

                    // Prepare NavHost to handle app routes
                    NavHost(
                        navController = navController,
                        startDestination = Screens.ElectionsScreen.route
                    ) {
                        composable(route = Screens.ElectionsScreen.route) {
                            ElectionsScreen(navController, mainViewModel)
                        }
                        composable(route = Screens.CameraPreviewScreen.route){
                            CameraPreview(navController)
                        }
                    }
                }
            }
        }
    }
}
