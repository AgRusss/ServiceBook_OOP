package com.codingwithjks.animeapp.Network

import com.codingwithjks.animeapp.Model.Car
import com.codingwithjks.animeapp.Model.Driver
import com.codingwithjks.animeapp.Model.DtoCar
import com.codingwithjks.animeapp.Model.DtoDriver
import com.codingwithjks.animeapp.Model.DtoServiceBook
import com.codingwithjks.animeapp.Model.ServiceBook
import javax.inject.Inject

class ApiServiceImp @Inject constructor(private val apiService: ApiService) {
    //реализация (интерфейса) получения машин из API
    suspend fun getCars(id: String): ArrayList<Car> = apiService.getCars(id)
    suspend fun createCar(car: DtoCar): Void = apiService.createCar(car)
    suspend fun updateCar(id: String, car: DtoCar): Void = apiService.updateCar(id, car)
    suspend fun deleteCar(id: String): Void = apiService.deleteCar(id)

    suspend fun getDrivers(): ArrayList<Driver> = apiService.getDrivers()
    suspend fun createDriver(driver: DtoDriver): Void = apiService.createDriver(driver)
    suspend fun updateDriver(id: String, driver: DtoDriver): Void = apiService.updateDriver(id, driver)
    suspend fun deleteDriver(id: String): Void = apiService.deleteDriver(id)

    suspend fun getServiceBooks(id: String): ArrayList<ServiceBook> = apiService.getServiceBooks(id)
    suspend fun createServiceBook(serviceBook: DtoServiceBook): Void = apiService.createServiceBook(serviceBook)
    suspend fun updateServiceBook(id: String, serviceBook: DtoServiceBook): Void = apiService.updateServiceBook(id, serviceBook)
    suspend fun deleteServiceBook(id: String): Void = apiService.deleteServiceBook(id)
}


