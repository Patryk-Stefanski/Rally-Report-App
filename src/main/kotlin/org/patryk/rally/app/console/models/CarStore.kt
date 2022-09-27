package org.patryk.rally.app.console.models

interface CarStore {
    fun findAll(): List<CarModel>
    fun findOne(id: Long): CarModel?
    fun create(car: CarModel)
    fun update(car: CarModel)
}