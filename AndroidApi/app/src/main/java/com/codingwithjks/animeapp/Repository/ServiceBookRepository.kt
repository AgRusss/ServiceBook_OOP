package com.codingwithjks.animeapp.Repository

import com.codingwithjks.animeapp.Model.DtoServiceBook
import com.codingwithjks.animeapp.Model.ServiceBook
import com.codingwithjks.animeapp.Network.ApiServiceImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ServiceBookRepository @Inject constructor(private val apiServiceImp: ApiServiceImp) {
    fun getServiceBook(id: String): Flow<ArrayList<ServiceBook>> = flow {
        val response = apiServiceImp.getServiceBooks(id)
        emit(response)
    }

    fun createServiceBook(serviceBook: DtoServiceBook): Flow<Void> = flow {
        val response = apiServiceImp.createServiceBook(serviceBook)
        emit(response)
    }

    fun updateServiceBook(serviceBook: DtoServiceBook): Flow<Void> = flow {
        val response = apiServiceImp.updateServiceBook(serviceBook.id as String, serviceBook)
        emit(response)
    }

    fun deleteServiceBook(id: String): Flow<Void> = flow {
        val response = apiServiceImp.deleteServiceBook(id)
        emit(response)
    }
}
