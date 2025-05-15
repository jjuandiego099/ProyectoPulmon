package com.example.proyecto

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.google.accompanist.pager.*
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val db = Firebase.firestore
    var nombre by remember { mutableStateOf("") }
    db.collection("usuarios").document(user!!.uid).get().addOnSuccessListener {
        nombre = it.getString("nombre").toString()}



    val seleccion = Color(0xFFB0CFEA)
    val borde = Color(0xFF2E4E69)

    val items = listOf(
        Pair("Estadísticas", Icons.Default.AddChart),
        Pair("Calendario", Icons.Default.DateRange),
        Pair("IA", Icons.Default.Star),
        Pair("Perfil", Icons.Default.Person)
    )

    val selectedItem = remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

    // Cambiar el estado de selectedItem cuando cambia el pagerState
    LaunchedEffect(pagerState.currentPage) {
        selectedItem.value = pagerState.currentPage
    }

    Scaffold(
        topBar = {
            val scrollBehavior =
                TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(nombre, maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        selectedItem.value = 3
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(3)
                        }

                    }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Usuario")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        auth.signOut()
                        navController.navigate("LoginScreen") {
                            popUpTo(0) { inclusive = true }
                        }
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Cerrar sesión"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, (label, icono) ->
                    NavigationBarItem(
                        selected = selectedItem.value == index,
                        onClick = {
                            selectedItem.value = index
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HorizontalPager(
                count = items.size,
                state = pagerState
            ) { page ->
                // Cargar las pantallas correspondientes según la página
                when (page) {
                    0 -> Estadisticas()
                    1 -> Calendario()
                    2 -> IA()
                    3 -> Perfil(cerrar = { auth.signOut()
                    navController.navigate("LoginScreen") {popUpTo(0) { inclusive = true }}})
                }
            }
        }
    }
}

