package org.wit.rove.views.Rove

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.wit.rove.databinding.ActivityRoveBinding
import org.wit.rove.main.MainApp
import org.wit.rove.models.Location
import org.wit.rove.models.RoveModel
import org.wit.rove.helpers.showImagePicker
import org.wit.rove.views.editLocation.EditLocationView
import timber.log.Timber

class RovePresenter(private val view: RoveView) {

    var visit = RoveModel()
    var app:MainApp = view.application as MainApp
    var binding: ActivityRoveBinding = ActivityRoveBinding.inflate(view.layoutInflater)
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var edit = false;

    init {
        if (view.intent.hasExtra("visit_edit")) {
            edit = true
            visit = view.intent.extras?.getParcelable("visit_edit")!!
            view.showVisit(visit)
        }
        registerImagePickerCallback()
        registerMapCallback()
    }

    fun doAddOrSave(title: String, description: String) {
        visit.title = title
        visit.description = description
        if (edit) {
            app.visits.update(visit)
        } else {
            app.visits.create(visit)
        }
        view.setResult(RESULT_OK)
        view.finish()
    }
    fun doCancel() {
        view.finish()
    }
    fun doDelete() {
        view.setResult(99)
        app.visits.delete(visit)
        view.finish()
    }
    fun doSelectImage() {
        showImagePicker(imageIntentLauncher,view)
    }
    fun doSetLocation() {
        val location = Location(51.8983, -8.4726, 15f)
        if (visit.zoom != 0f) {
            location.lat =  visit.lat
            location.lng = visit.lng
            location.zoom = visit.zoom
        }
        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }
    fun cacheVisit (title: String, description: String) {
        visit.title = title;
        visit.description = description
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            visit.image = result.data!!.data!!
                            view.contentResolver.takePersistableUriPermission(
                                visit.image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                            )
                            view.updateImage(visit.image)
                        }
                    }

                    AppCompatActivity.RESULT_CANCELED -> {}
                    else -> {}
                }}}

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            visit.lat = location.lat
                            visit.lng = location.lng
                            visit.zoom = location.zoom
                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> {} else -> {}
                }
            }
    }
}

