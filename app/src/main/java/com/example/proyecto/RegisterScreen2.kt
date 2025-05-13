package com.example.proyecto


import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun RegisterScreen2(navController: NavController) {
    var edad by remember { mutableStateOf("") }
    val generos = listOf("Masculino", "Femenino", "Otro", "Prefiero no decirlo")
    var textoNombre by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var anosFumando by remember { mutableStateOf("") }
    var cigarrillos by remember { mutableStateOf("") }

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
                text = "Queremos Conocerte Mas",
                fontSize = 24.sp, fontWeight = FontWeight.Bold,
                color = titulo
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = textoNombre,
                onValueChange = {
                    textoNombre = it
                },
                label = { Text("Nombre Completo") }, modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Nombre Completo",
                        tint = secundario
                    )
                },
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = edad,
                onValueChange = {
                    edad = it
                },
                label = { Text("Edad") }, modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Edad",
                        tint = secundario
                    )
                },
                shape = RoundedCornerShape(12.dp)
            )


            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = peso,
                onValueChange = {
                    peso = it
                },
                label = { Text("Peso") }, modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.FitnessCenter,
                        contentDescription = "Peso",
                        tint = secundario
                    )
                },
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = anosFumando,
                onValueChange = {
                    anosFumando = it
                },
                label = { Text("Cuantos años llevas fumando?") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Peso",
                        tint = secundario
                    )
                },
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = cigarrillos,
                onValueChange = {
                    cigarrillos = it
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
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            GenderDropdownMenu(
                options = generos,
                onOptionSelected = { genero ->
                    var genero = genero
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    navController.navigate("HomeScreen"){
                        popUpTo(0){inclusive=true}
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
            modifier = Modifier.fillMaxWidth()
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



