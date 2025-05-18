package com.example.proyecto

import android.util.Log
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log

@Composable
fun IA() {
    val primaryColor = Color(0xFF0D293F)
    val secondaryColor = Color(0xFF2E4E69)

    val yesNoFields = listOf(
        "Inmunodeficiencia", "Problema de respiración", "Consumo de alcohol",
        "Dolor de garganta", "Opresión en el pecho", "Historial familiar",
        "Historial familiar de tabaquismo", "Estrés inmunológico", "Enfermedad pulmonar",
        "Género", "Tabaquismo", "Descoloración de los dedos",
        "Estrés mental", "Exposición a la contaminación", "Enfermedad a largo plazo"
    )

    val toggleStates = remember {
        yesNoFields.associateWith { mutableStateOf(0) }.toMutableMap()
    }

    var age by remember { mutableStateOf(50f) }
    var textoresultado by remember { mutableStateOf(false) }
    var energy by remember { mutableStateOf(50f) }
    var oxygen by remember { mutableStateOf(95f) }

    var resultadoPrediccion by remember { mutableStateOf("") }

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
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp)
                        .align(Alignment.Center)
                )
            }
        }

        item {
            SliderWithLabel("Edad", age, 30f, 84f, primaryColor) { age = it }
        }
        item {
            SliderWithLabel("Nivel de Energia", energy, 24f, 83f, primaryColor) { energy = it }
        }
        item {
            SliderWithLabel("Saturacion de Oxigeno", oxygen, 89f, 99f, primaryColor) { oxygen = it }
        }
        //itera la lista de preguntas
        itemsIndexed(yesNoFields) { index, field -> //devuelve el index y el valor
            val state = toggleStates[field]!!   //asigna el valor del state
            YesNoField(
                label = field.replace('_', ' '),
                state = state,
                primaryColor = primaryColor,
                secondaryColor = secondaryColor,
                yesText = if (field == "Género") "Masculino" else "Sí", //es una caso especial para asiganr valor a genero
                noText = if (field == "Género") "Femenino" else "No"
            )
        }
        item {
            if (resultadoPrediccion.isNotEmpty()){
                Text(
                    text = "Resultado: $resultadoPrediccion",
                    fontSize = 18.sp,
                    color = if (resultadoPrediccion == "No tiene cáncer de pulmón") Color(0xFF4CAF50) else Color.Red,
                    fontWeight = FontWeight.Bold,)
            }

        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    textoresultado = true
                    val patientData = PatientData(
                        Age = age.toInt(),
                        Energy_Level = energy,
                        Oxygen_Saturation = oxygen,
                        Gender = toggleStates["Género"]?.value ?: 0,
                        Smoking = toggleStates["Tabaquismo"]?.value ?: 0,
                        Finger_Discoloration = toggleStates["Descoloración de los dedos"]?.value ?: 0,
                        Mental_Stress = toggleStates["Estrés mental"]?.value ?: 0,
                        Exposure_To_Pollution = toggleStates["Exposición a la contaminación"]?.value ?: 0,
                        Long_Term_Illness = toggleStates["Enfermedad a largo plazo"]?.value ?: 0,
                        Immune_Weakness = toggleStates["Inmunodeficiencia"]?.value ?: 0,
                        Breathing_Issue = toggleStates["Problema de respiración"]?.value ?: 0,
                        Alcohol_Consumption = toggleStates["Consumo de alcohol"]?.value ?: 0,
                        Throat_Discomfort = toggleStates["Dolor de garganta"]?.value ?: 0,
                        Chest_Tightness = toggleStates["Opresión en el pecho"]?.value ?: 0,
                        Family_History = toggleStates["Historial familiar"]?.value ?: 0,
                        Smoking_Family_History = toggleStates["Historial familiar de tabaquismo"]?.value ?: 0,
                        Stress_Immune = toggleStates["Estrés inmunológico"]?.value ?: 0
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                         resultadoPrediccion = ConsumirApi(patientData)




                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = secondaryColor),
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
    state: MutableState<Int>,
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
            RadioButton(
                selected = state.value == 1, //condicional para saber si esta seleccionado
                onClick = { state.value = 1 },  //asigna el valor 1 si esta seleccionado
                colors = RadioButtonDefaults.colors(
                    selectedColor = primaryColor,
                    unselectedColor = secondaryColor
                )
            )
            Text(yesText, color = secondaryColor)
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = state.value == 0,    //condicional para saber si esta seleccionado
                onClick = { state.value = 0 },  //asigna el valor 0 si esta seleccionado
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
