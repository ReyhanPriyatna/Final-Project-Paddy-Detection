package com.reyhanagus.deteksipadi.network

import com.reyhanagus.deteksipadi.network.responses.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    // fungsi untuk mengirimkan data saat register
    @POST("register")
    @FormUrlEncoded
    fun registerUser(
        @Header("Authorization") Bearer: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    // fungsi untuk mengirimkan data saat login
    @POST("login")
    @FormUrlEncoded
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    // fungsi untuk mengirimkan data recomendation
    @POST("crop-recommendations")
    @FormUrlEncoded
    fun getRecomendation(
        @Field("nitrogen") nitrogen: Int,
        @Field("phosphorous") phosphorous: Int,
        @Field("potassium") potassium: Int,
        @Field("temperature") temperature: Int,
        @Field("humidity") humidity: Int,
        @Field("ph") ph: Int,
        @Field("rainfall") rainfall: Int
    ): Call<RecomendationResponse>

    // fungsi untuk deteksi penyakit tanaman
    @Multipart
    @POST("rice-disease-detection")
    fun getDiseaseDetect(
        @Part image: MultipartBody.Part
    ): Call<DiseaseDetectResponse>

    // fungsi untuk mengirimkan data deteksi penyakit tanaman
    @POST("upload")
    @FormUrlEncoded
    fun addHistoryDisease(
        @Header("Authorization") Bearer: String,
        @Field("imageUrl") imageUrl: String,
        @Field("resultDetection") resultDetection: String
    ): Call<AddHistoryDiseaseResponse>

    // fungsi untuk mengambil data history penyakit tanaman
    @GET("history_detection")
    fun getHistoryDisease(
        @Header("Authorization") Bearer: String
    ): Call<List<GetHistoryResponseItem>>
}