package org.wit.rove.views.Rove

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.rove.R
import org.wit.rove.databinding.ActivityRoveBinding
import org.wit.rove.models.RoveModel
import timber.log.Timber.Forest.i
class RoveView: AppCompatActivity() {
    private lateinit var binding: ActivityRoveBinding
    private lateinit var presenter: RovePresenter
    var visit = RoveModel()
    //val visitRating = findViewById<RatingBar>(R.id.ratingBar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityRoveBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = RovePresenter(this)

        binding.chooseImage.setOnClickListener {
            presenter.cacheVisit(binding.visitTitle.text.toString(), binding.description.text.toString())
            presenter.doSelectImage()
        }

        binding.visitLocation.setOnClickListener {
            presenter.cacheVisit(binding.visitTitle.text.toString(), binding.description.text.toString())
            presenter.doSetLocation()
        }

        /*binding.btnAddRating.setOnClickListener {
            presenter.cacheVisit(binding.visitTitle.text.toString(), binding.description.text.toString())
            presenter.doSetRating()
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_visit, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        deleteMenu.isVisible = presenter.edit
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_save -> {
                if (binding.visitTitle.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_visit_title, Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    // presenter.cacheVisit(binding.visitTitle.text.toString(), binding.description.text.toString())
                    presenter.doAddOrSave(binding.visitTitle.text.toString(), binding.description.text.toString())
                }
            }
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showVisit(visit: RoveModel) {
        binding.visitTitle.setText(visit.title)
        binding.description.setText(visit.description)
        Picasso.get()
            .load(visit.image)
            .into(binding.visitImage)
        if (visit.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_visit_image)
        }
    }

    fun updateImage(image: Uri) {
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.visitImage)
        binding.chooseImage.setText(R.string.change_visit_image)
    }

}