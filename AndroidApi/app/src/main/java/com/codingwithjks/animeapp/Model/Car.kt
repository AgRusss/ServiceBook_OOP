package com.codingwithjks.animeapp.Model

data class Car (
    val id: String,
    val brand: String,
    val model: String,
    val numberOfCar: String,
    var serviceBooks: ArrayList<ServiceBook>,
    var drivers: ArrayList<Driver>,
) {
}

data class DtoCar (
    val id: String?,
    val brand: String,
    val model: String,
    val numberOfCar: String,
    var drivers: ArrayList<DriverId>?
) {
}

data class CarId (
    val id: String,
) {
}