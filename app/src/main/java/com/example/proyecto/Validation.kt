package com.example.proyecto

import java.time.LocalDate
import java.time.Period
import android.util.Patterns

fun ValidationEmail(email:String):Pair<Boolean, String>{
    return when{
        email.isEmpty()->Pair(false,"El correo es requerido")
        !Patterns.EMAIL_ADDRESS.matcher(email).matches()-> Pair(false,"Email inexistente")
        !email.endsWith("@unab.edu.co")->Pair(false, "El correo no es intitucional")
        else->
            Pair(true,"")
    }
}
fun ValidationPassword(password:String):Pair<Boolean, String>{
    return when{
        password.isEmpty()->Pair(false,"La contraseña es requerido")

        password.length<8->Pair(false, "La contraseña debe ser mayor a 8 caracteres")
        !password.any{it.isDigit()}-> Pair(false,"La contraseña debe contener al menos 1 numero")
        else->
            Pair(true,"")
    }
}
fun ValidationName(name:String):Pair<Boolean, String>{
    return when{
        name.isEmpty()->Pair(false,"El nombre es requerido")

        name.length<3->Pair(false, "El nombre debe ser mayor a 3 caracteres")

        else->
            Pair(true,"")
    }
}
fun ValidationConfirmation(password:String,password2:String):Pair<Boolean, String>{
    return when{
        password2.isEmpty()->Pair(false,"La confirmacion de contraseña es requerido")
        !password.equals(password2)->Pair(false,"Las contraseña no coinciden")


        else->
            Pair(true,"")
    }
}
fun ValidationPeso(peso: String): Pair<Boolean, String> {
    return when {
        peso.isEmpty() -> Pair(false, "El peso es requerido")
        peso.toFloatOrNull() == null -> Pair(false, "El peso debe ser un número válido")
        peso.toFloat() <= 0 -> Pair(false, "El peso debe ser mayor a 0")
        else -> Pair(true, "")
    }
}


fun validarEdadYFumar(fechaNacimiento: LocalDate, fechaFumar: LocalDate): Pair<Boolean, String> {
    val hoy = LocalDate.now()

    if (fechaNacimiento.isAfter(hoy)) {
        return Pair(false, "La fecha de nacimiento no puede estar en el futuro.")
    }

    if (fechaFumar.isAfter(hoy)) {
        return Pair(false, "La fecha desde que fumas no puede estar en el futuro.")
    }

    val edadActual = Period.between(fechaNacimiento, hoy).years
    val añosFumando = Period.between(fechaFumar, hoy).years

    if (edadActual < añosFumando + 12) {
        return Pair(false, "La edad debe ser al menos 12 años mayor que los años fumando.")
    }

    return Pair(true, "")
}
fun validarCigarrillosPorDia(cigarrillos: String): Pair<Boolean, String> {
    return when {
        cigarrillos.isBlank() ->
            Pair(false, "Por favor ingresa cuántos cigarrillos fumas al día.")

        !cigarrillos.all { it.isDigit() } ->
            Pair(false, "El campo de cigarrillos solo debe contener números.")

        cigarrillos.toIntOrNull() == null ->
            Pair(false, "Ingresa un número válido de cigarrillos.")

        cigarrillos.toInt() <= 0 ->
            Pair(false, "La cantidad debe ser mayor que cero.")

        cigarrillos.toInt() > 30 ->
            Pair(false, "La cantidad de cigarrillos por día no debe ser mayor a 100.")

        else -> Pair(true, "")
    }
}
fun validarGenero(genero: String): Pair<Boolean, String> {
    return when {
        genero.isBlank() ->
            Pair(false, "Seleccione un genereo.")

        else -> Pair(true, "")
    }
}



