package org.wit.rove.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.rove.R
import org.wit.rove.databinding.ActivityRoveBinding
import org.wit.rove.helpers.showImagePicker
import org.wit.rove.main.MainApp
import org.wit.rove.models.Location
import org.wit.rove.models.RoveModel
import timber.log.Timber.Forest.i

class RoveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoveBinding
    var visit = RoveModel()
    lateinit var app : MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityRoveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Rove Activity started...")

        if (intent.hasExtra("visit_edit")) {
            edit = true
            visit = intent.extras?.getParcelable("visit_edit")!!
            binding.visitTitle.setText(visit.title)
            binding.description.setText(visit.description)
            binding.btnAdd.setText(R.string.save_visit)
            Picasso.get()
                .load(visit.image)
                .into(binding.visitImage)
            if (visit.image != Uri.EMPTY) {
                binding.chooseImage.setText(R.string.change_visit_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            visit.title = binding.visitTitle.text.toString()
            visit.description = binding.description.text.toString()
            if (visit.title.isEmpty()) {
                Snackbar.make(it, R.string.enter_visit_title,Snackbar.LENGTH_LONG).show()
            } else {
                if (edit) {
                    app.visits.update(visit.copy())
                } else {
                    app.visits.create(visit.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
           showImagePicker(imageIntentLauncher, this)
        }

        binding.visitLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (visit.zoom != 0f){
                location.lat = visit.lat
                location.lng = visit.lng
                location.zoom = visit.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        registerImagePickerCallback()
        registerMapCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_visit, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            visit.image= image

                            Picasso.get()
                                .load(visit.image)
                                .into(binding.visitImage)
                            binding.chooseImage.setText(R.string.change_visit_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            visit.lat = location.lat
                            visit.lng = location.lng
                            visit.zoom = location.zoom
                        }
                    }
                    RESULT_CANCELED -> {} else -> {}
                }
            }
    }
}
