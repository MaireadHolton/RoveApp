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
import org.wit.rove.models.RoveModel
import timber.log.Timber.Forest.i

class RoveActivity : AppCompatActivity() {

    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

    private lateinit var binding: ActivityRoveBinding
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    var visit = RoveModel()
    lateinit var app : MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityRoveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Rove Activity started...")

        binding.visitLocation.setOnClickListener {
            i ("Set Location Pressed")
        }

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
           showImagePicker(imageIntentLauncher)
        }
        registerImagePickerCallback()

        binding.visitLocation.setOnClickListener {
            val launcherIntent = Intent(this, MapActivity::class.java)
            mapIntentLauncher.launch(launcherIntent)
        }
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
                            visit.image = result.data!!.data!!
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
            { i("Map Loaded") }
    }
}