package com.example.proyecto

import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun IA(navController: NavController) {
    val primaryColor = Color(0xFF0D293F)
    val secondaryColor = Color(0xFF2E4E69)

    // Lista de campos con respuestas Sí/No
    val yesNoFields = listOf(
        "Inmunodeficiencia", "Problema de respiración", "Consumo de alcohol",
        "Dolor de garganta", "Opresión en el pecho", "Historial familiar",
        "Historial familiar de tabaquismo", "Estrés inmunológico", "Enfermedad pulmonar",
        "Género", "Tabaquismo", "Descoloración de los dedos",
        "Estrés mental", "Exposición a la contaminación", "Enfermedad a largo plazo"
    )

    // Inicialización de los estados con valores por defecto
    val toggleStates = remember {
        yesNoFields.associateWith { mutableStateOf(false) }.toMutableMap()
    }

    var age by remember { mutableStateOf(30f) }
    var energy by remember { mutableStateOf(50f) }
    var oxygen by remember { mutableStateOf(95f) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Predicción",
                    fontSize = 26.sp,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold, // Negrita
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp) // Padding top y bottom
                        .align(Alignment.Center) // Centrado horizontal con Box
                )
            }
        }

        item {
            SliderWithLabel("Edad", age, 0f, 100f, primaryColor) { age = it }
        }
        item {
            SliderWithLabel("Nivel de Energia", energy, 0f, 100f, primaryColor) { energy = it }
        }
        item {
            SliderWithLabel("Saturacion de Oxigeno", oxygen, 80f, 100f, primaryColor) { oxygen = it }
        }

        // Usando itemsIndexed
        itemsIndexed(yesNoFields) { index, field ->
            val state = toggleStates[field]!!
            YesNoField(
                label = field.replace('_', ' '),
                state = state,
                primaryColor = primaryColor,
                secondaryColor = secondaryColor,
                yesText = if (field == "Gender") "Masculino" else "Sí",
                noText = if (field == "Gender") "Femenino" else "No"
            )
        }
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors
                    (containerColor = secondaryColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Predecir",
                    fontSize = 16.sp

                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun YesNoField(
    label: String,
    state: MutableState<Boolean>,
    primaryColor: Color,
    secondaryColor: Color,
    yesText: String,
    noText: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            color = primaryColor
        )
        Row {
            // RadioButton para Sí
            RadioButton(
                selected = state.value,
                onClick = { state.value = true },
                colors = RadioButtonDefaults.colors(
                    selectedColor = primaryColor,
                    unselectedColor = secondaryColor
                )
            )
            Text(yesText, color = secondaryColor)
            Spacer(modifier = Modifier.width(8.dp))

            // RadioButton para No
            RadioButton(
                selected = !state.value,
                onClick = { state.value = false },
                colors = RadioButtonDefaults.colors(
                    selectedColor = primaryColor,
                    unselectedColor = secondaryColor
                )
            )
            Text(noText, color = secondaryColor)

        }

    }
}

@Composable
fun SliderWithLabel(
    label: String,
    value: Float,
    min: Float,
    max: Float,
    color: Color,
    onValueChange: (Float) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "$label: ${value.toInt()}", color = color)
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = min..max,
            colors = SliderDefaults.colors(
                thumbColor = color,
                activeTrackColor = color
            )
        )
    }
}








