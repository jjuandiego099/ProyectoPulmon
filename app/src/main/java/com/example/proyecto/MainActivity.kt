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
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProyectoTheme {

                var auth= Firebase.auth
                var currentUser= auth.currentUser
                var startDestiation = "LoginScreen"
                if(currentUser !=null){
                    startDestiation = "HomeScreen"
                }else{

                    startDestiation="loginScreen"
                }
                val navController = rememberNavController()





                NavHost(
                    navController = navController,
                    startDestination = startDestiation,
                    modifier = Modifier.fillMaxSize()
                ) {

                    composable("LoginScreen") { LoginScreen(navController) }
                    composable("RegisterScreen") { RegisterScreen(navController) }
                    composable("HomeScreen") { HomeScreen(navController) }
                    composable("RegisterScreen2") { RegisterScreen2(navController) }


                }
            }


        }
    }
}

