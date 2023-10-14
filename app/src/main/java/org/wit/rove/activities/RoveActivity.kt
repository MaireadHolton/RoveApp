package org.wit.rove.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import org.wit.rove.databinding.ActivityRoveBinding
import org.wit.rove.main.MainApp
import org.wit.rove.models.RoveModel
import timber.log.Timber
import timber.log.Timber.Forest.i

class RoveActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRoveBinding
    var visit = RoveModel()
    lateinit var app : MainApp
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRoveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Rove Activity started...")
        binding.btnAdd.setOnClickListener() {
            visit.title = binding.visitTitle.text.toString()
            visit.description = binding.description.text.toString()
            if (visit.title.isNotEmpty()) {
                app.visits.add(visit.copy())
                i("add Button Pressed: ${visit}")
                for (i in app.visits.indices)
                {i("Visit[$i]:${this.app.visits[i]}}")}
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}