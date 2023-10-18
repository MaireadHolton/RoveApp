package org.wit.rove.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.rove.databinding.CardVisitBinding
import org.wit.rove.models.RoveModel

interface RoveListener {
    fun onRoveClick(visit: RoveModel)
}
class RoveAdapter constructor(private var visits: List<RoveModel>,
    private val listener: RoveListener) :
    RecyclerView.Adapter<RoveAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardVisitBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val visit = visits[holder.adapterPosition]
        holder.bind(visit, listener)
    }

    override fun getItemCount(): Int = visits.size

    class MainHolder(private val binding: CardVisitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(visit: RoveModel, listener: RoveListener) {
            binding.visitTitle.text = visit.title
            binding.description.text = visit.description
            Picasso.get().load(visit.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onRoveClick(visit) }
        }
    }
}