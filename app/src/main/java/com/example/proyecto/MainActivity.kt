package com.example.proyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto.ui.theme.ProyectoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoTheme {
                val navController = rememberNavController()
                val startDestiation = "IA"


                NavHost(
                    navController = navController,
                    startDestination = startDestiation,
                    modifier = Modifier.fillMaxSize()
                ) {

                    composable("Login") { LoginScreen(navController) }
                    composable("Register") { RegisterScreen(navController) }
                    composable("HomeScreen") { HomeScreen(navController) }

                    composable("RegisterScreen2") { RegisterScreen2(navController) }
                    composable("IA") { IA(navController) }

                }
            }


        }
    }
}

