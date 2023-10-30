package org.wit.rove.views.roveList

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.rove.activities.RoveMapsActivity
import org.wit.rove.main.MainApp
import org.wit.rove.models.RoveModel
import org.wit.rove.views.Rove.RoveView


class RoveListPresenter(val view: RoveListView) {
    var app: MainApp
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private var position: Int=0

    init {
        app = view.application as MainApp
        registerMapCallback()
        registerRefreshCallback()
    }

    fun getVisits() = app.visits.findAll()

    fun doAddVisit() {
        val launcherIntent = Intent(view, RoveView::class.java)
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doEditVisit(visit: RoveModel, pos: Int) {
        val launcherIntent = Intent(view, RoveView::class.java)
        launcherIntent.putExtra("visit_edit", visit)
        position = pos
        refreshIntentLauncher.launch(launcherIntent)
    }

    fun doShowVisitsMap(){
        val launcherIntent = Intent(view, RoveMapsActivity::class.java)
        mapIntentLauncher.launch(launcherIntent)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            view.registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) {
                if (it.resultCode == Activity.RESULT_OK) view.onRefresh()
                else // Deleting
                    if (it.resultCode == 99) view.onDelete(position)
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher = view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {}
    }
}
