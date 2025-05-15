package com.example.proyecto


import android.util.Log
import android.widget.CalendarView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.RadioButton


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SmokeFree
import androidx.compose.material.icons.filled.SmokingRooms
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun RegisterScreen2(navController: NavController) {
    var fechan by remember { mutableStateOf(LocalDate.now()) }
    val generos = listOf("Masculino", "Femenino", "Otro", "Prefiero no decirlo")
    var textoNombre by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var anosFumando by remember { mutableStateOf(LocalDate.now()) }
    var cigarrillos by remember { mutableStateOf("") }
    var MessagePeso by remember { mutableStateOf("") }
    var MessageName by remember { mutableStateOf("") }
    var MessageNac by remember { mutableStateOf("") }
    var MessageGenero by remember { mutableStateOf("") }
    var MessageCigarillos by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }


    val titulo = Color(0xFF0D293F)
    val secundario = Color(0xFF2E4E69)

    //0xFF495D91

    Scaffold(topBar = {


        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = titulo
                    )
                }
            })
    }) { innerPading ->
        Column(
            modifier = Modifier
                .padding(innerPading)
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "Register",
                modifier = Modifier.size(150.dp),
                colorFilter = ColorFilter.tint(titulo)
            )
            Text(
                text = "Queremos Conocerte Más",
                fontSize = 24.sp, fontWeight = FontWeight.Bold,
                color = titulo
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = textoNombre,
                onValueChange = {
                    if (it.all { it.isLetter() || it.isWhitespace() }) {
                        textoNombre = it
                    }
                },
                keyboardOptions = KeyboardOptions.Default, // o KeyboardOptions(keyboardType = KeyboardType.Text)
                singleLine = true,
                label = { Text("Nombre Completo") }, modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Nombre Completo",
                        tint = secundario
                    )
                },
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    if (MessageName.isNotEmpty()) {
                        Text(MessageName, color = Color.Red)
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = peso,
                onValueChange = {
                    if (it.all { it.isDigit() }) {
                        peso = it
                    }
                },
                label = { Text("Peso") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.FitnessCenter,
                        contentDescription = "Peso",
                        tint = secundario
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    if (MessagePeso.isNotEmpty()) {
                        Text(MessagePeso, color = Color.Red)
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            CalendarDatePicker(
                label = "Fecha de nacimiento: $fechan",
                onDateSelected = { date -> fechan = date })
            if (MessageNac.isNotEmpty()) {
                Text(MessageNac, color = Color.Red, fontSize = 11.sp)

            } else {
                Spacer(modifier = Modifier.height(20.dp))
            }
            CalendarDatePicker(
                label = "Fumas desde:  $anosFumando",
                onDateSelected = { date ->
                    anosFumando = date
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = cigarrillos,
                onValueChange = {
                    if (it.all { it.isDigit() }) {
                        cigarrillos = it
                    }
                },
                label = { Text("Cuantos Cigarrillos fumas diario?") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.SmokingRooms,
                        contentDescription = "Peso",
                        tint = secundario
                    )
                },
                shape = RoundedCornerShape(12.dp),
                supportingText = {
                    if (MessageCigarillos.isNotEmpty()) {
                        Text(MessageCigarillos, color = Color.Red)
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            GenderDropdownMenu(
                options = generos,
                onOptionSelected = {
                    genero = it
                }
            )
            if (MessageGenero.isNotEmpty()) {
                Text("Elija un genero", color = Color.Red, fontSize = 11.sp)
            }
            if (error.isNotEmpty()) {

                Text(
                    error,
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    var BooleanName: Boolean = ValidationName(textoNombre).first
                    MessageName = ValidationName(textoNombre).second
                    var BooleanPeso: Boolean = ValidationPeso(peso).first
                    MessagePeso = ValidationPeso(peso).second
                    var BooleanCigarrillos: Boolean = validarCigarrillosPorDia(cigarrillos).first
                    MessageCigarillos = validarCigarrillosPorDia(cigarrillos).second
                    var BooleanFechas: Boolean = validarEdadYFumar(fechan, anosFumando).first
                    MessageNac = validarEdadYFumar(fechan, anosFumando).second
                    var BooleanGenero: Boolean = validarGenero(genero).first
                    MessageGenero = validarGenero(genero).second

                    if (BooleanName && BooleanPeso && BooleanCigarrillos && BooleanFechas && BooleanGenero) {
                        guardarDatos(
                            nombre = textoNombre,
                            peso = peso,
                            fechaNacimiento = fechan,
                            fechaInicioFumar = anosFumando,
                            cigarrillosPorDia = cigarrillos,
                            genero = genero,
                            onSuccess = {
                                navController.navigate("HomeScreen") {
                                    popUpTo(0) { inclusive = true }
                                }
                            },
                            onError = { error = it })
                    }


                },
                colors = ButtonDefaults.buttonColors
                    (containerColor = secundario),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Continuar",
                    fontSize = 16.sp
                )
            }


        }
    }

}

@Composable
fun GenderDropdownMenu(

    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth(),

            ) {
            Text(
                text = "Género: $selectedOption"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selectedOption = option
                        expanded = false
                        onOptionSelected(option)
                    },

                    )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarDatePicker(
    label: String,
    onDateSelected: (LocalDate) -> Unit
) {
    // Estado para saber si el diálogo está abierto
    var openDialog by remember { mutableStateOf(false) }
    // Fecha elegida, inicializada a hoy
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // El campo que muestra la fecha y abre el diálogo
    OutlinedButton(
        onClick = { openDialog = true },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = label)
    }

    // El diálogo Compose Material3 con selector de año/mes
    if (openDialog) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        )

        DatePickerDialog(
            onDismissRequest = { openDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    // Cuando confirmen, actualizamos selectedDate
                    datePickerState.selectedDateMillis?.let { millis ->
                        selectedDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onDateSelected(selectedDate)
                    }
                    openDialog = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                DatePicker(state = datePickerState)
            }
        }
    }
}






