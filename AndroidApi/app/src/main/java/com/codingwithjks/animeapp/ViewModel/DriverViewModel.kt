package com.codingwithjks.animeapp.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithjks.animeapp.Model.Car
import com.codingwithjks.animeapp.Model.Driver
import com.codingwithjks.animeapp.Model.DtoDriver
import com.codingwithjks.animeapp.Repository.CarRepository
import com.codingwithjks.animeapp.Repository.DriverRepository
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
class DriverViewModel @Inject constructor(private val driverRepository: DriverRepository) :
    ViewModel() {
    val driverResponse: MutableLiveData<ArrayList<Driver>> = MutableLiveData()

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun getDrivers() {
        viewModelScope.launch {
            driverRepository.getDriver().catch { e ->
                Log.d("main", "${e.message}") // обработка ошибки
            }.collectLatest { response -> //собираем данные
                driverResponse.value = response //и записываем в поле animetopresponse.value
            }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun createDriver(driver: DtoDriver) {
        viewModelScope.launch {
            driverRepository.createDriver(driver).catch { e ->
                Log.d("main", "${e.message}") // обработка ошибки
            }.collectLatest { response -> //собираем данные

            }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun updateDriver(driver: DtoDriver) {
        viewModelScope.launch {
            driverRepository.updateDriver(driver).catch { e ->
                Log.d("main", "${e.message}") // обработка ошибки
            }.collectLatest { response -> //собираем данные

            }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun deleteDriver(id: String) {
        viewModelScope.launch {
            driverRepository.deleteDriver(id).catch { e ->
                Log.d("main", "${e.message}") // обработка ошибки
            }.collectLatest { response -> //собираем данные

            }
        }
    }
}