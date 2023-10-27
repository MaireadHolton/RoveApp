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
        //visits.add(RoveModel("Mabel Lane", ("Fully accessible entrance, lots of space in front bar area, disabled toilet available, no disabled parking on site but available in Supervalu behind the bar")))
        //visits.add(RoveModel("The lodge bar", ("Limited parking on site, ramp free access from front entrance")))
        //visits.add(RoveModel("Collins bar", ("Ramp free access, disabled parking on site, disabled toilets")))
    }
}