package com.example.proyecto


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto.ui.theme.ProyectoTheme


@Composable
fun LoginScreen(navcontroller: NavController) {
    var textoCorreo by remember { mutableStateOf("") }
    var textoContrasena by remember { mutableStateOf("") }
    val titulo=Color(0xFF0D293F)
    val secundario=Color(0xFF2E4E69)

    Scaffold { valuesPadding ->
        Column(
            modifier = Modifier
                .padding(valuesPadding)
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row (modifier = Modifier.padding(bottom = 10.dp)){
                Text(
                    text = "MiPulmon",
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Bold,
                    color = titulo
                )

            }

            Image(
                painter = painterResource(R.drawable.pulmon),
                contentDescription = "Logo Corazon",
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Iniciar Sesion",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = secundario
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = textoCorreo,
                onValueChange = {
                    textoCorreo = it
                },
                label = { Text("Correo Electronico") }, modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "email",
                        tint = secundario
                    )
                },
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = textoContrasena,
                onValueChange = {
                    textoContrasena = it
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
            Button(
                onClick = {

                },
                colors = ButtonDefaults.buttonColors
                    (containerColor = secundario),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Iniciar Sesion",
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            TextButton(onClick = {
                navcontroller.navigate("Register")
            }) {
                Text(
                    text = "¿No tienes una cuenta? Registrate",
                    color = secundario
                )
            }

        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    ProyectoTheme {
//        LoginScreen()
    }

}
