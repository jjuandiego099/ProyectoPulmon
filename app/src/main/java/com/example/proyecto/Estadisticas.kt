package com.example.proyecto



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter


import com.github.mikephil.charting.components.Legend



@Composable
fun Estadisticas(lista: List<RegistroCigarrillos>, click: () -> Unit = {}) {
    val primaryColor = Color(0xFF0D293F) // Azul oscuro
    val secondaryColor = Color(0xFF2E4E69) // Azul claro
    var cigarrillos by remember { mutableStateOf(0) }
    obtenerCigarrillosHoy (onResultado = { cigarrillos =it},onError = {})
    var MesssageCigarrillos by remember { mutableStateOf("") }
    var consejo = obtenerConsejoAzar()








    Scaffold { innnerPading ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innnerPading).padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Text(
                    text = "Estadísticas",
                    color = primaryColor,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp).padding(top = 5.dp, bottom = 8.dp))
            }
            item {
                if (lista.isEmpty()) {
                    Text("No hay datos disponibles.")
                } else {
                    GraficoBarrasCigarrillos(lista)
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Botón restar
                    ElevatedButton(
                        onClick = { cigarrillos = cigarrillos.toInt() - 1 },
                        modifier = Modifier.size(65.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = secondaryColor,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 12.dp,
                            focusedElevation = 10.dp,
                            hoveredElevation = 10.dp
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Remove, contentDescription = "Restar")
                    }

                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre botón y campo

                    // Campo de texto numérico
                    OutlinedTextField(
                        value = "Cigarrillos: $cigarrillos",
                        onValueChange = {
                            if (it.all { it.isDigit() }) {
                                cigarrillos = it.toInt()
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f),
                        supportingText = {
                            if (MesssageCigarrillos.isNotEmpty()) {
                                Text(
                                    MesssageCigarrillos,
                                    color = if (MesssageCigarrillos == "Actualizado correctamente") Color(
                                        0xFF4CAF50
                                    ) else Color.Red
                                )
                            }
                        },
                        label = { Text("Cigarrillos el dia de Hoy") }
                    )

                    Spacer(modifier = Modifier.width(8.dp)) // Espacio entre campo y botón

                    // Botón sumar
                    ElevatedButton(
                        onClick = { cigarrillos = cigarrillos.toInt() + 1 },
                        modifier = Modifier.size(65.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = secondaryColor,
                            contentColor = Color.White
                        ),
                        elevation = ButtonDefaults.elevatedButtonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 12.dp,
                            focusedElevation = 10.dp,
                            hoveredElevation = 10.dp
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Sumar")
                    }


                }
                Spacer(modifier = Modifier.height(16.dp))

            }
            item {
                Button(
                    onClick = {
                        if (cigarrillos.toInt() >= 0) {
                            cigarrillosDia(cigarrillos)
                            MesssageCigarrillos = "Actualizado correctamente"
                            click()







                        } else {
                            MesssageCigarrillos = "Cigarrillos no pueden ser negativos"
                        }
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = secondaryColor,
                        contentColor = Color.White
                    ), modifier = Modifier.size(170.dp, 50.dp)
                ) {
                    Text(text = "Guardar")
                }
            }
            item {
                Spacer(modifier = Modifier.height(26.dp))
                Text(
                    "Consejo del Dia", color = primaryColor,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp))
                Image(painter=painterResource(R.drawable.pulmoni),modifier=Modifier.size(200.dp), contentDescription = "gif")
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    consejo, color = primaryColor,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }


        }
    }

}

@Composable
fun GraficoBarrasCigarrillos(lista: List<RegistroCigarrillos>) {
    val context = LocalContext.current
    val barChart = remember { BarChart(context) }
    //se crea la instancia barchart con el contextto actual

    //se ejecuta cada vez que lista cambia
    LaunchedEffect(lista) {
        //se configura el grafico
        barChart.legend.apply {
            horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER // Centrar la leyenda
            textSize = 14f
            textColor = android.graphics.Color.BLACK
        }

        val entries = lista.mapIndexed { index, registro ->
            BarEntry(index.toFloat(), registro.cantidad.toFloat()) // Eje Y = cantidad de cigarrillos
            //El eje X es el índice (posición) y el eje Y es la cantidad.
        }

        val fechas = lista.map { it.fecha } // Extraer fechas
        //Crea un conjunto de datos para el gráfico con las barras y las configura (color, texto)
        val dataSet = BarDataSet(entries, "Cigarrillos por día").apply {
            color = 0xFFB0CFEA.toInt()
            valueTextSize = 12f
        }

        //Asigna los datos al gráfico y elimina el texto de descripción.
        val barData = BarData(dataSet)
        barChart.data = barData
        barChart.description.text = ""

        // Configurar el eje X con las fechas
        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(fechas) // Mostrar fechas en el eje X
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM //La posición del eje X es en la parte inferior.
        xAxis.granularity = 1f // Asegurar que cada valor sea único

        // Configurar el eje Y
        val yAxis = barChart.axisLeft
        yAxis.axisMinimum = 0f // Asegurar que empiece en 0
        barChart.axisRight.isEnabled = false // Ocultar eje Y derecho

        barChart.invalidate()
    }

    AndroidView(factory = { barChart }, modifier = Modifier.fillMaxWidth().height(300.dp))
}







