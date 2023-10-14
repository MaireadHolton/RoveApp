package org.wit.rove

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber
import timber.log.Timber.Forest.i

class RoveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rove)

        Timber.plant(Timber.DebugTree())
        i("Rove Activity started..")
    }
}