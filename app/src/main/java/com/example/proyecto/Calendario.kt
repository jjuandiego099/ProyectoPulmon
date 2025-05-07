package com.example.proyecto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun Calendario() {
    val titulo = Color(0xFF0D293F)
    val secundario = Color(0xFF2E4E69)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título en mayúsculas
        Text(
            text = "DÍAS LIBRES DE HUMO",
            color = titulo,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )

        // Descripción con texto normal
        Text(
            text = "Dejar de fumar mejora la salud casi de inmediato: en solo 20 minutos baja la presión arterial, en 24 horas se reduce el riesgo de ataque cardíaco, y con el tiempo los pulmones y el sistema inmunológico se fortalecen notablemente.",
            color = secundario,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(250.dp)
                .padding(top = 8.dp)
        ) {
            CircularProgressIndicator(
                progress = 0.95f,
                color = titulo,
                strokeWidth = 8.dp,
                modifier = Modifier.fillMaxSize()
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Texto "LIBRE DE HUMO" en mayúsculas
                Text("LIBRE DE HUMO", color = titulo, fontWeight = FontWeight.Bold)
                Text("DESDE", color = secundario, fontSize = 12.sp)

                Spacer(modifier = Modifier.height(12.dp))

                // Números de tiempo con formato uniforme
                Row(verticalAlignment = Alignment.Bottom) {
                    TiempoTexto("2", "AÑOS", titulo, secundario)
                    TiempoTexto("4", "MESES", titulo, secundario)
                    TiempoTexto("26", "DÍAS", titulo, secundario)
                    TiempoTexto("6", "HORAS", titulo, secundario)
                    TiempoTexto("33", "MINUTOS", titulo, secundario)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // "58 segundos" en la parte inferior
                TiempoTexto("58", "SEGUNDOS", titulo, secundario)
            }
        }
    }
}

@Composable
fun TiempoTexto(valor: String, etiqueta: String, colorValor: Color, colorEtiqueta: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Text(text = valor, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = colorValor)
        Text(text = etiqueta, fontSize = 10.sp, color = colorEtiqueta)
    }
}
