package com.codingwithjks.animeapp.Network

import com.codingwithjks.animeapp.Model.Car
import com.codingwithjks.animeapp.Model.Driver
import com.codingwithjks.animeapp.Model.DtoCar
import com.codingwithjks.animeapp.Model.DtoDriver
import com.codingwithjks.animeapp.Model.DtoServiceBook
import com.codingwithjks.animeapp.Model.ServiceBook
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    //посылка гет запроса
    //через retrofit
    @GET("car/driver/{id}")
    suspend fun getCars(@Path("id") id: String): ArrayList<Car>

    @POST("car")
    suspend fun createCar(@Body car: DtoCar): Void

    @PATCH("car/{id}")
    suspend fun updateCar(@Path("id") id: String, @Body car: DtoCar): Void

    @DELETE("car/{id}")
    suspend fun deleteCar(@Path("id") id: String): Void



    @GET("driver")
    suspend fun getDrivers(): ArrayList<Driver>

    @POST("driver")
    suspend fun createDriver(@Body driver: DtoDriver): Void

    @PATCH("driver/{id}")
    suspend fun updateDriver(@Path("id") id: String, @Body driver: DtoDriver): Void

    @DELETE("driver/{id}")
    suspend fun deleteDriver(@Path("id") id: String): Void



    @GET("servicebook/car/{id}")
    suspend fun getServiceBooks(@Path("id") id: String): ArrayList<ServiceBook>

    @POST("servicebook")
    suspend fun createServiceBook(@Body serviceBook: DtoServiceBook): Void

    @PATCH("servicebook/{id}")
    suspend fun updateServiceBook(@Path("id") id: String, @Body serviceBook: DtoServiceBook): Void

    @DELETE("servicebook/{id}")
    suspend fun deleteServiceBook(@Path("id") id: String): Void
}