package com.example.proyecto



import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
fun RegisterScreen(navcontroller: NavController) {
    var textoCorreo by remember { mutableStateOf("") }
    var textoContrasena by remember { mutableStateOf("") }
    var textoNombre by remember { mutableStateOf("") }
    var textoContrasena2 by remember { mutableStateOf("") }
    val titulo=Color(0xFF0D293F)
    val secundario=Color(0xFF2E4E69)
    //0xFF495D91

    Scaffold(topBar = {

        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = { navcontroller.popBackStack() }) {
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
                text = "Registrar Usuario",
                fontSize = 24.sp, fontWeight = FontWeight.Bold,
                color = titulo
            )
            Spacer(modifier = Modifier.height(24.dp))

            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = textoCorreo,
                onValueChange = {
                    textoCorreo=it
                },
                label = { Text("Correo Electronico") }, modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Correo Electronico",
                        tint = secundario
                    )
                },
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = textoContrasena,
                onValueChange = {
                    textoContrasena=it
                },
                label = { Text("Contraseña") }, modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Contraseña",
                        tint = secundario
                    )
                },
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = textoContrasena2,
                onValueChange = {
                    textoContrasena2=it
                },
                label = { Text("Confirmar Contraseña") }, modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Confirmar Contraseña",
                        tint = secundario
                    )
                },
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    navcontroller.navigate("RegisterSrceen2")
                },
                colors = ButtonDefaults.buttonColors
                    (containerColor = secundario),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)

            ) {
                Text(
                    text = "Registrarse",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

            }
            TextButton(onClick = {
                navcontroller.popBackStack()
            }) {
                Text(
                    text = "Si tengo una cuenta",
                    color = secundario
                )
            }


        }
    }

}


