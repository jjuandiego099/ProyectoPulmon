package com.example.proyecto


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.proyecto.ui.theme.ProyectoTheme

@Composable
fun HomeScreen(navController: NavController? = null) {
    val seleccion=Color(0xFFB0CFEA)
    val borde=Color(0xFF2E4E69)
    val items = listOf(
        Pair("Estadistica", Icons.Default.AddChart),
        Pair("Calendario", Icons.Default.DateRange),
        Pair("IA", Icons.Default.Star),
        Pair("Perfil", Icons.Default.Person)

    )

    // Estado que guarda el índice seleccionado
    val selectedItem = remember { mutableStateOf(0) }
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, (label, icono) ->
                    NavigationBarItem(
                        selected = selectedItem.value == index,
                        onClick = { selectedItem.value = index },
                        icon = { Icon(icono, contentDescription = label) },
                        label = { Text(label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = borde,
                            unselectedIconColor = borde,
                            indicatorColor = seleccion
                        )
                    )
                }
            }
        }
    )
    { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Home Screen", fontSize = 50.sp)
        }
    }
}

@Preview
@Composable
fun PreviewHomeScreen(

) {
    ProyectoTheme {
        HomeScreen()
    }
}

