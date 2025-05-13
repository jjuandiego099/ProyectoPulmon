package com.example.proyecto


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
        name.isEmpty()->Pair(false,"El nombre es requerido es requerido")

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
