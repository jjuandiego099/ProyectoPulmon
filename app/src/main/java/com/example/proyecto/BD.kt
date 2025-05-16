@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.example.proyecto

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale
import com.google.firebase.firestore.SetOptions
import java.util.Calendar
import java.util.TimeZone

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
fun obtenerCigarrillosHoy(
    onResultado: (Int) -> Unit,
    onError: (Exception) -> Unit
) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val db = FirebaseFirestore.getInstance()

    // Obtener la fecha de hoy en UTC para evitar problemas de desfase de zona horaria
    val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    formatoFecha.timeZone = TimeZone.getTimeZone("UTC") // ✅ Usar UTC para evitar errores
    val fechaHoy = formatoFecha.format(Calendar.getInstance().time)

    val docRef = db.collection("usuarios")
        .document(uid)
        .collection("cigarrillos")
        .document(fechaHoy)

    docRef.get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                val cigarrillos = document.getLong("cigarrillos")?.toInt() ?: 0
                onResultado(cigarrillos)
            } else {
                // Crear el documento con cigarrillos = 0 solo si no existe
                docRef.set(mapOf("cigarrillos" to 0))
                    .addOnSuccessListener { onResultado(0) }
                    .addOnFailureListener { onError(it) }
            }
        }
        .addOnFailureListener { e ->
            onError(e)
        }
}
data class RegistroCigarrillos(val fecha: String, val cantidad: Int)

fun obtenerTodosLosRegistrosCigarrillos(
    onResultado: (List<RegistroCigarrillos>) -> Unit,
    onError: (Exception) -> Unit
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
            }.sortedBy { it.fecha } // Ordenar en orden cronológico
            onResultado(lista)
        }
        .addOnFailureListener { e ->
            onError(e)
        }
}


fun obtenerTiempoDesdeUltimoCigarrillo(
    onResultado: (Map<String, Long>) -> Unit,
    onError: (Exception) -> Unit
) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val db = FirebaseFirestore.getInstance()

    db.collection("usuarios")
        .document(uid)
        .collection("cigarrillos")
        .orderBy("fecha", Query.Direction.DESCENDING)
        .limit(1)
        .get()
        .addOnSuccessListener { querySnapshot ->
            val ultimaFechaStr = querySnapshot.documents.firstOrNull()?.getString("fecha") ?: ""

            if (ultimaFechaStr.isNotEmpty()) {
                try {
                    // Convertir la fecha de Firebase a Date
                    val formatoFecha = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val fechaUltimoCigarrillo = formatoFecha.parse(ultimaFechaStr)

                    val ahora = Calendar.getInstance().time
                    val diferenciaMillis = ahora.time - fechaUltimoCigarrillo.time

                    // Calcular cada unidad de tiempo
                    val tiempoTranscurrido = mapOf(
                        "años" to diferenciaMillis / (1000L * 60 * 60 * 24 * 365),
                        "meses" to (diferenciaMillis / (1000L * 60 * 60 * 24 * 30)) % 12,
                        "días" to (diferenciaMillis / (1000 * 60 * 60 * 24)) % 30,
                        "horas" to (diferenciaMillis / (1000 * 60 * 60)) % 24,
                        "minutos" to (diferenciaMillis / (1000 * 60)) % 60,
                        "segundos" to (diferenciaMillis / 1000) % 60
                    )

                    onResultado(tiempoTranscurrido)
                } catch (e: Exception) {
                    onError(Exception("Error al procesar la fecha"))
                }
            } else {
                onResultado(mapOf(
                    "años" to 0, "meses" to 0, "días" to 0,
                    "horas" to 0, "minutos" to 0, "segundos" to 0
                ))
            }
        }
        .addOnFailureListener { e -> onError(e) }
}
fun calcularCigarrillosPromedio(
    onResultado: (Int) -> Unit, // Ahora devuelve un Int en lugar de un Double
    onError: (Exception) -> Unit
) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val db = FirebaseFirestore.getInstance()

    db.collection("usuarios")
        .document(uid)
        .collection("cigarrillos")
        .get()
        .addOnSuccessListener { querySnapshot ->
            var totalCigarrillos = 0
            val totalDias = querySnapshot.size()

            querySnapshot.documents.forEach { doc ->
                val cigarrillos = doc.getLong("cigarrillos") ?: 0
                totalCigarrillos += cigarrillos.toInt()
            }

            val promedio = if (totalDias > 0) (totalCigarrillos.toDouble() / totalDias).toInt() else 0
            onResultado(promedio)
        }
        .addOnFailureListener { e -> onError(e) }
}








