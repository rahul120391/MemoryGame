package com.example.colourmemory.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colourmemory.databinding.LayoutHighScoreRowItemBinding
import com.example.colourmemory.model.Scores

class ScoreAdapter(private val list:List<Scores>):RecyclerView.Adapter<ScoreAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding:LayoutHighScoreRowItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(scores: Scores){
            binding.apply {
                txtRank.text = scores.rank.toString()
                txtPlayerName.text = scores.name
                txtScore.text = scores.score
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutHighScoreRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}