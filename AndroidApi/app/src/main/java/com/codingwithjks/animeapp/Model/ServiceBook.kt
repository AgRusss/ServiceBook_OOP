package com.codingwithjks.animeapp.Model

data class ServiceBook (
    val id: String,
    val date: String,
    val mileage: Int,
    val description: String,
    val car: Car,
) {
}

data class DtoServiceBook (
    val id: String?,
    val date: String,
    val mileage: Int,
    val description: String,
    val car: CarId?,
) {
}
