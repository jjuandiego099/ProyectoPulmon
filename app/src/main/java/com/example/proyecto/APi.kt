package com.example.proyecto

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response


data class PatientData(
    val Age: Int,
    val Energy_Level: Float,
    val Oxygen_Saturation: Float,
    val Gender: Int,
    val Smoking: Int,
    val Finger_Discoloration: Int,
    val Mental_Stress: Int,
    val Exposure_To_Pollution: Int,
    val Long_Term_Illness: Int,
    val Immune_Weakness: Int,
    val Breathing_Issue: Int,
    val Alcohol_Consumption: Int,
    val Throat_Discomfort: Int,
    val Chest_Tightness: Int,
    val Family_History: Int,
    val Smoking_Family_History: Int,
    val Stress_Immune: Int
)

data class PredictionResponse(
    val prediction: Int,
    val result: String
)
//retrofit usa interfaces para comunicarse con kotlin
//Define cómo interactuar con la API
interface PredictionApi {
    //manda la solicitud a la api
    @POST("/app")
    // el suspend vuelve la funcion asincrona
    suspend fun predict(@Body data: PatientData): Response<PredictionResponse>
    //body pasa los datos  como el cuerpo de la peticion
}

// Crea la instancia de Retrofit
object RetrofitInstance {
    val api: PredictionApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://proyectoia-6vpv.onrender.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PredictionApi::class.java) //lo usamos para obtener la clase de la interfaz.
    }
}

// Función que se llama desde IA
suspend fun ConsumirApi(data: PatientData): String {
    Log.d("API_ENVIO", "Enviando datos: $data")
    return try {
        val response = RetrofitInstance.api.predict(data)
        if (response.isSuccessful) {
            response.body()?.result ?: "Sin respuesta del servidor"
        } else {
            "Error ${response.code()}: ${response.message()}"
        }
    } catch (e: Exception) {
        "Excepción: ${e.localizedMessage ?: "Error desconocido"}"
    }
}
