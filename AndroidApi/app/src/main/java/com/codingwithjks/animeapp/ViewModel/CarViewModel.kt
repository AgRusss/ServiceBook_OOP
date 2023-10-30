package com.codingwithjks.animeapp.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingwithjks.animeapp.Model.Car
import com.codingwithjks.animeapp.Model.DtoCar
import com.codingwithjks.animeapp.Model.DtoDriver
import com.codingwithjks.animeapp.Repository.CarRepository
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
class CarViewModel @Inject constructor(private val carRepository: CarRepository) :
    ViewModel() {
    val carResponse: MutableLiveData<ArrayList<Car>> = MutableLiveData()

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun getCars(id: String) {
        viewModelScope.launch {
            carRepository.getCars(id).catch { e ->
                Log.d("main", "${e.message}") // обработка ошибки
            }.collectLatest { response -> //собираем данные
                carResponse.value = response //и записываем в поле animetopresponse.value
            }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun createCar(car: DtoCar) {
        viewModelScope.launch {
            carRepository.createCar(car).catch { e ->
                Log.d("main", "${e.message}") // обработка ошибки
            }.collectLatest { response -> //собираем данные

            }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun updateCar(car: DtoCar) {
        viewModelScope.launch {
            carRepository.updateCar(car).catch { e ->
                Log.d("main", "${e.message}") // обработка ошибки
            }.collectLatest { response -> //собираем данные

            }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    fun deleteCar(id: String) {
        viewModelScope.launch {
            carRepository.deleteCar(id).catch { e ->
                Log.d("main", "${e.message}") // обработка ошибки
            }.collectLatest { response -> //собираем данные

            }
        }
    }
}