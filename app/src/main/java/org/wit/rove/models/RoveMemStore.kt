package org.wit.rove.models

import timber.log.Timber.Forest.i
var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class RoveMemStore: RoveStore {

    val visits = ArrayList<RoveModel>()

    override fun findAll(): List<RoveModel> {
        return visits
    }

    override fun create(visit: RoveModel) {
        visits.add(visit)
        logAll()
    }

    override fun update(visit : RoveModel){
        var foundVisit: RoveModel? = visits.find { v -> v.id == visit.id}
        if (foundVisit != null) {
            foundVisit.title = visit.title
            foundVisit.description = visit.description
            //foundVisit.rating = visit.rating
            foundVisit.image = visit.image
            foundVisit.lat = visit.lat
            foundVisit.lng = visit.lng
            foundVisit.zoom = visit.zoom
            logAll()
        }
    }

    override fun findById(id:Long) : RoveModel? {
        val foundVisit: RoveModel? = visits.find { it.id == id }
        return foundVisit
    }

    private fun logAll() {
        visits.forEach { i("${it}")}
    }

    override fun delete(visit: RoveModel) {
        visits.remove(visit)
    }
}