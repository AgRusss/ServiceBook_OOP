package com.codingwithjks.animeapp.Repository

import com.codingwithjks.animeapp.Model.Driver
import com.codingwithjks.animeapp.Model.DtoDriver
import com.codingwithjks.animeapp.Network.ApiServiceImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DriverRepository @Inject constructor(private val apiServiceImp: ApiServiceImp) {
    fun getDriver(): Flow<ArrayList<Driver>> = flow {
        val response = apiServiceImp.getDrivers()
        emit(response)
    }

    fun createDriver(driver: DtoDriver): Flow<Void> = flow {
        val response = apiServiceImp.createDriver(driver)
        emit(response)
    }

    fun updateDriver(driver: DtoDriver): Flow<Void> = flow {
        val response = apiServiceImp.updateDriver(driver.id as String, driver)
        emit(response)
    }

    fun deleteDriver(id: String): Flow<Void> = flow {
        val response = apiServiceImp.deleteDriver(id)
        emit(response)
    }
}
