package com.example.proyecto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@Preview
@Composable
fun Perfil(cerrar: () -> Unit = {}) {

    val primaryColor = Color(0xFF0D293F) // Azul oscuro
    val secondaryColor = Color(0xFF2E4E69) // Azul claro
    val auth = Firebase.auth
    val user = auth.currentUser
    val db = Firebase.firestore
    var nombre by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf(0L) }
    var cigarrillosPorDia by remember { mutableStateOf(0L) }
    var fechaNacimiento by remember { mutableStateOf("") }
    var tiempoCigarrillos by remember { mutableStateOf(emptyMap<String, Long>()) }

    LaunchedEffect(Unit) {
        obtenerTiempoDesdeUltimoCigarrillo(
            onResultado = { tiempoCigarrillos = it },
            onError = {  }
        )
    }


    var genero by remember { mutableStateOf("") }

    val correo = user?.email ?: "No existe usuario"

    //se obtiene los datos del usuario
    db.collection("usuarios").document(user!!.uid).get().addOnSuccessListener {
        nombre = it.getString("nombre").toString()
        fechaNacimiento = it.getString("fechaNacimiento").toString()

        peso = it.getLong("peso")?: 0L
        genero = it.getString("genero").toString()

    }


    val titulo = "Perfil de Usuario"
    val infoLabel = "Información Personal"
    val edad = calcularEdad(fechaNacimiento)
//    val libreHumo = calcularEdad(fechaNacimiento)


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text(
                text = titulo,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
        }

        item {

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Usuario",
                modifier = Modifier.size(100.dp),
                tint = primaryColor
            )
        }
        item {

            Text(
                text = infoLabel,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
        }
        item {
            calcularCigarrillosPromedio(onResultado = {cigarrillosPorDia=it.toLong()}, onError = {})
            //se muestra la innformacion con los valores obtenidos

            Campo("Correo", correo)
            Spacer(modifier = Modifier.height(20.dp))
            Campo("Nombre", nombre)
            Spacer(modifier = Modifier.height(20.dp))
            Campo("Edad", edad.toString())
            Spacer(modifier = Modifier.height(20.dp))
            Campo("Peso", peso.toString())
            Spacer(modifier = Modifier.height(20.dp))
            Campo("Cigarrillos promedio diarios", cigarrillosPorDia.toString())
            Spacer(modifier = Modifier.height(20.dp))
            Campo("Días libre de humo",tiempoCigarrillos["días"].toString() )
        }
        item {
            Button(
                onClick = { cerrar() },
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Cerrar Sesión", color = Color.White)
            }
        }


    }
}



@Composable
fun Campo(label: String, valor: String) {
    val primaryColor = Color(0xFF0D293F) // Azul oscuro
    val secondaryColor = Color(0xFF2E4E69) // Azul claro
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "$label:",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = primaryColor
        )
        Text(valor, fontSize = 20.sp, color = secondaryColor)
    }
}

fun calcularEdad(fechaNacimiento: String?): Int? {
    if (fechaNacimiento.isNullOrEmpty()) return null
    //si fecha de nacimiento existe y no es nula o vacia
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val fechaNacimientoDate = LocalDate.parse(fechaNacimiento, formatter)
        val hoy = LocalDate.now()
        Period.between(fechaNacimientoDate, hoy).years //se devuelve la diferencia en años
    } catch (e: Exception) {
        null
    }
}
