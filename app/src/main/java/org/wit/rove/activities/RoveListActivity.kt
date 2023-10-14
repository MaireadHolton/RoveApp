package org.wit.rove.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.rove.R
import org.wit.rove.databinding.ActivityRoveListBinding
import org.wit.rove.databinding.CardVisitBinding
import org.wit.rove.main.MainApp
import org.wit.rove.models.RoveModel

class RoveListActivity : AppCompatActivity() {

    lateinit var app: MainApp
    private lateinit var binding: ActivityRoveListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoveListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = RoveAdapter(app.visits)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, RoveActivity::class.java)
                getResult.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.visits.size)
            }
        }
}

class RoveAdapter constructor(private var visits: List<RoveModel>) :
    RecyclerView.Adapter<RoveAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardVisitBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val visit = visits[holder.adapterPosition]
        holder.bind(visit)
    }

    override fun getItemCount(): Int = visits.size

    class MainHolder(private val binding : CardVisitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(visit: RoveModel) {
            binding.visitTitle.text = visit.title
            binding.description.text = visit.description
        }
    }
}