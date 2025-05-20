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
    var lista by remember { mutableStateOf<List<RegistroCigarrillos>>(emptyList()) }// lista de objetos registro cigarrillos
    var tiempoCigarrillos by remember { mutableStateOf(emptyMap<String, Long>()) } //mapa de tiempo del ultimo cigarrillo
    var nombre by remember { mutableStateOf("") }
    db.collection("usuarios").document(user!!.uid).get().addOnSuccessListener {
        nombre = it.getString("nombre").toString()  //muestra el nombre del usuario en el topbar
    }
    //se cragan ambas funciones aqui para que esten precargadas desde que se abre el homeScreen

    LaunchedEffect(Unit) {
        obtenerTodosLosRegistrosCigarrillos(
            onResultado = { datos -> lista = datos },
            onError = { }
        )
    }
    LaunchedEffect(Unit) {
        obtenerTiempoDesdeUltimoCigarrillo(
            onResultado = { tiempoCigarrillos = it },
            onError = { }
        )
    }


    val seleccion = Color(0xFFB0CFEA)
    val borde = Color(0xFF2E4E69)

    val items = listOf(
        Pair("Estadísticas", Icons.Default.AddChart),
        Pair("Calendario", Icons.Default.DateRange),
        Pair("IA", Icons.Default.Star),
        Pair("Perfil", Icons.Default.Person)
    )

    val selectedItem = remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(initialPage = 0)//el estado del pager
    val coroutineScope =
        rememberCoroutineScope() //se usa para lanzar animaciones al cambiar la página

    // Cambiar el estado de selectedItem cuando cambia el pagerState
    LaunchedEffect(pagerState.currentPage) {
        selectedItem.value =
            pagerState.currentPage //actualiza el value del bottombar con el del pager
    }

    Scaffold(
        topBar = {
            val scrollBehavior =
                //aparece y desaparece el topbar con el scroll
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
                            pagerState.animateScrollToPage(3)//viaja al pager 3 el del perfil
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
                items.forEachIndexed { index, (label, icono) -> //recorre cada parte de la lista item
                    NavigationBarItem(
                        selected = selectedItem.value == index, //if que comprueba si el item seleccionado es el mismo que el index
                        onClick = {
                            selectedItem.value = index  //actualiza el value del bottombar
                            //se inicia una corutina para animar el pager
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index) // se desplace animadamente a la página con el índice index.
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
                count = items.size, //se define el numero de pantallas
                state = pagerState  //El estado controla cuál página está activa
            ) { page ->
                // Cargar las pantallas correspondientes según la página
                when (page) {
                    0 -> Estadisticas(lista, click = {
                        obtenerTodosLosRegistrosCigarrillos(
                            onResultado = { datos -> lista = datos },
                            onError = { }
                        )


                        })

                        1 -> Calendario(tiempoCigarrillos)
                        2 -> IA()
                        3 -> Perfil(cerrar = {
                        auth.signOut()
                        navController.navigate("LoginScreen") { popUpTo(0) { inclusive = true } }
                        //el poopupto 0 elimina el historial de pantallas incluida la actual
                        //se regresa a la pantalla de login y se cierra sesion
                    })
                    }
                }
            }
        }
    }

