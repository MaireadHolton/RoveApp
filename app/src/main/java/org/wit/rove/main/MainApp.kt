package org.wit.rove.main

import android.app.Application
import org.wit.rove.models.RoveJSONStore
import org.wit.rove.models.RoveMemStore
import org.wit.rove.models.RoveStore
import org.wit.rove.models.RoveModel
import timber.log.Timber
import timber.log.Timber.Forest.i
class MainApp : Application() {

    lateinit var visits: RoveStore
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        visits = RoveJSONStore(applicationContext)
        i("Rove started")
        //visits.add(RoveModel("Mabel Lane", ("Bar/ Restaurant")))
        //visits.add(RoveModel("The lodge bar", ("Bar/ Restaurant")))
        //visits.add(RoveModel("Collins bar", ("Hotel Bar/ Restaurant")))
    }
}