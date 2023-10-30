package com.codingwithjks.animeapp.Model

data class Driver (
    val id: String,
    val fullName: String,
    val cars: ArrayList<Car>,
) {
}
data class DtoDriver (
    val id: String?,
    val fullName: String,
) {
}

data class DriverId(
    val id: String
) {
}
