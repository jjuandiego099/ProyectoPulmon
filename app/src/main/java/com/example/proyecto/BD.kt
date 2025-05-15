package com.example.proyecto

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale
import com.google.firebase.firestore.SetOptions

fun guardarDatos(
    nombre: String,
    peso: String,
    fechaNacimiento: LocalDate,
    fechaInicioFumar: LocalDate,
    cigarrillosPorDia: String,
    genero: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val user = FirebaseAuth.getInstance().currentUser
    if (user == null) {
        onError("Usuario no autenticado.")
        return
    }

    val db = FirebaseFirestore.getInstance()

    val datos = hashMapOf(
        "nombre" to nombre,
        "peso" to peso.toInt(),
        "fechaNacimiento" to fechaNacimiento.toString(),
        "fechaInicioFumar" to fechaInicioFumar.toString(),
        "cigarrillosPorDia" to cigarrillosPorDia.toInt(),
        "genero" to genero,
        "uid" to user.uid
    )

    db.collection("usuarios")
        .document(user.uid)
        .set(datos)
        .addOnSuccessListener { onSuccess() }
        .addOnFailureListener { e -> onError(e.message ?: "Error desconocido") }
}


fun cigarrillosDia(cigarrillos: Int) {
    val user = FirebaseAuth.getInstance().currentUser
    val uid = user?.uid ?: return
    val db = FirebaseFirestore.getInstance()

    val fechaHoy = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val fechaCompleta = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())

    val docRef = db.collection("usuarios")
        .document(uid)
        .collection("cigarrillos")
        .document(fechaHoy)

    val datos = hashMapOf(
        "fecha" to fechaCompleta,
        "cigarrillos" to cigarrillos
    )

    docRef.set(datos, SetOptions.merge()) // Actualiza si existe, crea si no
}
data class RegistroCigarrillos(val fecha: String, val cantidad: Int)

fun obtenerDatosCigarrillosPorDia(

) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val db = FirebaseFirestore.getInstance()

    db.collection("usuarios")
        .document(uid)
        .collection("cigarrillos")
        .get()
        .addOnSuccessListener { querySnapshot ->
            val lista = querySnapshot.documents.mapNotNull { doc ->
                val fecha = doc.id // ID es la fecha
                val cantidad = doc.getLong("cigarrillos")?.toInt()
                if (cantidad != null) RegistroCigarrillos(fecha, cantidad) else null
            }.sortedBy { it.fecha } // orden cronológico

        }


}








