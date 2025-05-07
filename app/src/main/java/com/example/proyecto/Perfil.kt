package com.example.proyecto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

@Preview
@Composable
fun Perfil() {

    val primaryColor = Color(0xFF0D293F) // Azul oscuro
    val secondaryColor = Color(0xFF2E4E69) // Azul claro

    val titulo = "Perfil de Usuario"
    val infoLabel = "Información Personal"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = titulo,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor
        )

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Usuario",
            modifier = Modifier.size(100.dp),
            tint = primaryColor
        )

        Text(
            text = infoLabel,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor
        )



        Campo("Correo", "andres@example.com")
        Campo("Nombre", "Efrain")
        Campo("Edad", "28 años")
        Campo("Peso", "70 kg")
        Campo("Cigarrillos promedio diarios", "5")
        Campo("Días libre de humo", "12")

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* Acción de cerrar sesión */ },
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Cerrar Sesión", color = Color.White)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PerfilPreview() {
    Perfil()
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
