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
        visit.id = getId()
        visits.add(visit)
        logAll()
    }

    private fun logAll() {
        visits.forEach { i("${it}")}
    }
}