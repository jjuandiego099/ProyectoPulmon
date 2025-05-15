package com.example.proyecto


import android.util.Log
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

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import java.util.*



@Composable
fun Estadisticas() {
    val primaryColor = Color(0xFF0D293F) // Azul oscuro
    val secondaryColor = Color(0xFF2E4E69) // Azul claro
    var cigarrillos by remember { mutableStateOf(0) }
    var MesssageCigarrillos by remember { mutableStateOf("") }
    var consejo = obtenerConsejoAzar()
    Scaffold { innnerPading ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innnerPading),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Text(
                    text = "Estadísticas",
                    color = primaryColor,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
            item { }
            item {
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
                        value = "Cigarrillos: ${cigarrillos}",
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
                            obtenerDatosCigarrillosPorDia(

                            )


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








