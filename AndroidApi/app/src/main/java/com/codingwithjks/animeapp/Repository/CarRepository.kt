package com.codingwithjks.animeapp.Repository

import com.codingwithjks.animeapp.Model.Car
import com.codingwithjks.animeapp.Model.DtoCar
import com.codingwithjks.animeapp.Model.DtoDriver
import com.codingwithjks.animeapp.Network.ApiServiceImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CarRepository @Inject constructor(private val apiServiceImp: ApiServiceImp) {
    fun getCars(id: String): Flow<ArrayList<Car>> = flow {
        val response = apiServiceImp.getCars(id)
        emit(response)
    }

    fun createCar(car: DtoCar): Flow<Void> = flow {
        val response = apiServiceImp.createCar(car)
        emit(response)
    }

    fun updateCar(car: DtoCar): Flow<Void> = flow {
        val response = apiServiceImp.updateCar(car.id as String, car)
        emit(response)
    }

    fun deleteCar(id: String): Flow<Void> = flow {
        val response = apiServiceImp.deleteCar(id)
        emit(response)
    }
}