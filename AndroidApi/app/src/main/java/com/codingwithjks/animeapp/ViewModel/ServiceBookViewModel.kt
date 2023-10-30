package com.codingwithjks.animeapp.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithjks.animeapp.Model.Car
import com.codingwithjks.animeapp.Model.DtoCar
import com.codingwithjks.animeapp.Model.DtoServiceBook
import com.codingwithjks.animeapp.Model.ServiceBook
import com.codingwithjks.animeapp.Repository.CarRepository
import com.codingwithjks.animeapp.Repository.ServiceBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

//класс в котором получаем данные
//корутины нужны для асинхронной работы
//мы не ждем пока запрос придет
@HiltViewModel
class ServiceBookViewModel @Inject constructor(private val serviceBookRepository: ServiceBookRepository) :
    ViewModel() {
    val serviceBookResponse: MutableLiveData<ArrayList<ServiceBook>> = MutableLiveData()

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun getServiceBooks(id: String) {
        viewModelScope.launch {
            serviceBookRepository.getServiceBook(id).catch { e ->
                Log.d("main", "${e.message}") // обработка ошибки
            }.collectLatest { response -> //собираем данные
                serviceBookResponse.value = response //и записываем в поле animetopresponse.value
            }
        }
    }


    @ExperimentalCoroutinesApi
    @FlowPreview
    fun createServiceBook(serviceBook: DtoServiceBook) {
        viewModelScope.launch {
            serviceBookRepository.createServiceBook(serviceBook).catch { e ->
                Log.d("main", "${e.message}") // обработка ошибки
            }.collectLatest { response -> //собираем данные

            }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun updateServiceBook(serviceBook: DtoServiceBook) {
        viewModelScope.launch {
            serviceBookRepository.updateServiceBook(serviceBook).catch { e ->
                Log.d("main", "${e.message}") // обработка ошибки
            }.collectLatest { response -> //собираем данные

            }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun deleteServiceBook(id: String) {
        viewModelScope.launch {
            serviceBookRepository.deleteServiceBook(id).catch { e ->
                Log.d("main", "${e.message}") // обработка ошибки
            }.collectLatest { response -> //собираем данные

            }
        }
    }
}