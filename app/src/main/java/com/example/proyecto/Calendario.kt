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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


@Composable
fun Calendario( tiempo: Map<String, Long>) {
    val titulo = Color(0xFF0D293F)
    val secundario = Color(0xFF2E4E69)

    var tiempoCigarrillos by remember { mutableStateOf(tiempo) }// se le asigna el valor inicial del timepo transcurrido desde el ultimo momento

    LaunchedEffect(Unit) {//es asincrono


        while (isActive) { // Evita bloqueo infinito de la UI
            delay(1000L)//se ejecuta cada 1 segundo
            //crea una copia del mapa de tiempoCigarrillos y actualiza los valores
            tiempoCigarrillos = tiempoCigarrillos.toMutableMap().apply {    //se configura los valores de minutos horas y segundos
                val segundos = this["segundos"] ?: 0
                val minutos = this["minutos"] ?: 0
                val horas = this["horas"] ?: 0
                val días = this["días"] ?: 0
                val meses = this["meses"] ?: 0
                val años = this["años"] ?: 0

                this["segundos"] = segundos + 1
                //se verifica que cuando un valor llegue a 60 los demas se reinicien
                if (this["segundos"]!! >= 60) {
                    this["minutos"] = minutos + 1
                    this["segundos"] = 0
                }

                if (this["minutos"]!! >= 60) {
                    this["horas"] = horas + 1
                    this["minutos"] = 0
                }

                if (this["horas"]!! >= 24) {
                    this["días"] = días + 1
                    this["horas"] = 0
                }

                if (this["días"]!! >= 30) { // Suponiendo meses de 30 días
                    this["meses"] = meses + 1
                    this["días"] = 0
                }

                if (this["meses"]!! >= 12) {
                    this["años"] = años + 1
                    this["meses"] = 0
                }
            }
        }
    }

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
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Descripción con texto normal
        Text(
            text = "Dejar de fumar mejora la salud casi de inmediato: en solo 20 minutos baja la presión arterial, en 24 horas se reduce el riesgo de ataque cardíaco, y con el tiempo los pulmones y el sistema inmunológico se fortalecen notablemente.",
            color = secundario,
            fontSize = 20.sp,
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
                progress = 1f,
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
                    //se transforma el valor de Long a string
                    //se verifica que no sea nuelo y si no se le asigna 0
                    // se verifica si es 1 el valor para que el texto sea plural
                    TiempoTexto("${tiempoCigarrillos["años"] ?: 0L}", if ((tiempoCigarrillos["años"] ?: 0L) == 1L) " AÑO" else " AÑOS", titulo, secundario)
                    TiempoTexto("${tiempoCigarrillos["meses"] ?: 0L}", if ((tiempoCigarrillos["meses"] ?: 0L) == 1L) " MES" else " MESES", titulo, secundario)
                    TiempoTexto("${tiempoCigarrillos["días"] ?: 0L}", if ((tiempoCigarrillos["días"] ?: 0L) == 1L) " DÍA" else " DÍAS", titulo, secundario)
                    TiempoTexto("${tiempoCigarrillos["horas"] ?: 0L}", if ((tiempoCigarrillos["horas"] ?: 0L) == 1L) " HORA" else " HORAS", titulo, secundario)
                    TiempoTexto("${tiempoCigarrillos["minutos"] ?: 0L}", if ((tiempoCigarrillos["minutos"] ?: 0L) == 1L) " MINUTO" else " MINUTOS", titulo, secundario)
                }

                Spacer(modifier = Modifier.height(8.dp))

                TiempoTexto("${tiempoCigarrillos["segundos"] ?: 0L}", if ((tiempoCigarrillos["segundos"] ?: 0L) == 1L) " SEGUNDO" else " SEGUNDOS", titulo, secundario)
    }}
}}

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
