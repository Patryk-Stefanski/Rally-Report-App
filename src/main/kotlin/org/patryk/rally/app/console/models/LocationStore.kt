package org.patryk.rally.app.console.models

interface LocationStore {
    fun findAll(): List<LocationModel>
    fun findOne(id: Long): LocationModel?
    fun create(location : LocationModel)
    fun update(location: LocationModel)
}