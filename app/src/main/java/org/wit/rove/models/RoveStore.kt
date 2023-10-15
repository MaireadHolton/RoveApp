package org.wit.rove.models

interface RoveStore {
        fun findAll(): List<RoveModel>
        fun create(visit: RoveModel)

        fun update(visit: RoveModel)
}