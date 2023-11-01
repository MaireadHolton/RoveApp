package org.wit.rove.views.roveList

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.rove.R
import android.widget.RatingBar
import org.wit.rove.databinding.ActivityRoveListBinding
import org.wit.rove.main.MainApp
import org.wit.rove.models.RoveModel

class RoveListView : AppCompatActivity(), RoveListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityRoveListBinding
    lateinit var presenter: RoveListPresenter
    private var position: Int = 0
    //val ratingBar = findViewById<RatingBar>(R.id.ratingBar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoveListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        presenter = RoveListPresenter(this)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadVisits()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> { presenter.doAddVisit() }
            R.id.item_map -> { presenter.doShowVisitsMap() }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRoveClick(visit: RoveModel, position: Int) {
        this.position = position
        presenter.doEditVisit(visit, this.position)
    }

    private fun loadVisits() {
        binding.recyclerView.adapter = RoveAdapter(presenter.getVisits(), this)
        onRefresh()
    }

    fun onRefresh() {
        binding.recyclerView.adapter?.
        notifyItemRangeChanged(0,presenter.getVisits().size)
    }

    fun onDelete(position : Int) {
        binding.recyclerView.adapter?.notifyItemRemoved(position)
    }

}