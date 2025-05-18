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
    val user = FirebaseAuth.getInstance().currentUser   //  se obtiene el usuario actual
    if (user == null) {
        onError("Usuario no autenticado.")      // se verifica que exista un usuario autenticado
        return
    }

    val db = FirebaseFirestore.getInstance()

    val datos = hashMapOf(      //almacena clave valor
        "nombre" to nombre,
        "peso" to peso.toInt(),
        "fechaNacimiento" to fechaNacimiento.toString(),
        "fechaInicioFumar" to fechaInicioFumar.toString(),
        "cigarrillosPorDia" to cigarrillosPorDia.toInt(),
        "genero" to genero,
        "uid" to user.uid
    )

    db.collection("usuarios")   //coleccion de usuarios de firebase
        .document(user.uid)
        .set(datos)             //se guarda los datos
        .addOnSuccessListener { onSuccess() }   //se captura el evento de exito
        .addOnFailureListener { e -> onError(e.message ?: "Error desconocido") }
}


fun cigarrillosDia(cigarrillos: Int) {  //se usa para guardar en firebase los cigarrillos por dia y mostrarlos en grafica
    val user = FirebaseAuth.getInstance().currentUser
    val uid = user?.uid ?: return
    val db = FirebaseFirestore.getInstance()

    val fechaHoy = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) //identificador
    val fechaCompleta = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()) //valor a guardar

    val docRef = db.collection("usuarios")
        .document(uid)  //donde se guardara
        .collection("cigarrillos")  //la coleccion cigarrillos se crea automaticamente para cada usuario
        .document(fechaHoy) // se guardan segun la fecha

    val datos = hashMapOf(
        "fecha" to fechaCompleta,
        "cigarrillos" to cigarrillos
    )

    docRef.set(datos, SetOptions.merge()) // Actualiza si existe, crea si no, guarda la fecha y cigarrillos
}


fun obtenerCigarrillosHoy(  //Verifica la cantidad de cigarrillos que tiene hoy
    onResultado: (Int) -> Unit,
    onError: (Exception) -> Unit
) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val db = FirebaseFirestore.getInstance()

    // Obtener la fecha de hoy en UTC para evitar problemas de desfase de zona horaria
    val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val fechaCompleta = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
    formatoFecha.timeZone = TimeZone.getTimeZone("UTC") //  Usar UTC para evitar errores
    val fechaHoy = formatoFecha.format(Calendar.getInstance().time)

    val docRef = db.collection("usuarios")
        .document(uid)
        .collection("cigarrillos")
        .document(fechaHoy)

    docRef.get()
        .addOnSuccessListener { document -> //agrega un listener para verificar si existe el documento
            if (document.exists()) {
                val cigarrillos = document.getLong("cigarrillos")?.toInt() ?: 0 //obtiene el numero de cigarrillos sino lo designa a 0
                onResultado(cigarrillos)    //obtiene el numero de cigarrillos
            } else {
                // Crear el documento con cigarrillos = 0 solo si no existe
                docRef.set(mapOf(
                    "fecha" to fechaCompleta,
                    "cigarrillos" to 0))
                    .addOnSuccessListener { onResultado(0) }
                    .addOnFailureListener { onError(it) }
            }
        }
        .addOnFailureListener { e ->
            onError(e)
        }
}


data class RegistroCigarrillos(val fecha: String, val cantidad: Int)//objeto clave valor de fecha y cigarrillo

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
            //Los elementos se agregan implícitamente en la función mapNotNull al retornar objetos no nulos
            val lista = querySnapshot.documents.mapNotNull { doc -> //se recorre cada documento de la coleccion
                val fecha = doc.id // ID es la fecha
                val cantidad = doc.getLong("cigarrillos")?.toInt()
                if (cantidad != null) RegistroCigarrillos(fecha, cantidad) else null    // se extrae los valores si cantidad es diferente de null
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
        .orderBy("fecha", Query.Direction.DESCENDING)   // Ordena los documentos por el campo "fecha" de forma descendente, es decir, de más reciente a más antiguo.
        .limit(1)   //limita el resultado a 1 documento
        .get()
        .addOnSuccessListener { querySnapshot ->
            val ultimaFechaStr = querySnapshot.documents.firstOrNull()?.getString("fecha") ?: ""    //devulve el 1 documento y su fecha o null si no hay nignuno

            if (ultimaFechaStr.isNotEmpty()) {
                try {
                    // Convertir la fecha de Firebase a Date
                    val formatoFecha = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    val fechaUltimoCigarrillo = formatoFecha.parse(ultimaFechaStr)  //se obtiene la ultima fecha en el formato deseado

                    val ahora = Calendar.getInstance().time
                    val diferenciaMillis = ahora.time - fechaUltimoCigarrillo.time  //devuelve la diferencia de tiempo en milisegundos

                    // Calcular cada unidad de tiempo
                    val tiempoTranscurrido = mapOf( //transforma la diferencia de tiempo en un mapa de tiempo
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
            val totalDias = querySnapshot.size()    //obtiene el numero total de documentos de la coleccion cigarrillos

            querySnapshot.documents.forEach { doc ->
                val cigarrillos = doc.getLong("cigarrillos") ?: 0
                totalCigarrillos += cigarrillos.toInt() //acumulador del total de cigarrillos
            }

            val promedio = if (totalDias > 0) (totalCigarrillos.toDouble() / totalDias).toInt() else 0 // se calcula el promedio y se vuelve entero
            onResultado(promedio)   //se devuelve el promedio en la lambda
        }
        .addOnFailureListener { e -> onError(e) }
}








