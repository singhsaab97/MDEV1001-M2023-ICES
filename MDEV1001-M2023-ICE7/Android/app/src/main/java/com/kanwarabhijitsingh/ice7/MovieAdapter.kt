package com.kanwarabhijitsingh.ice7

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(private val movies: List<Movie>) :
	RecyclerView.Adapter<MovieViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.view_holder_movie, parent, false)
		return MovieViewHolder(view)
	}

	override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
		val movie = movies[position]
		holder.bind(movie)
		holder.itemView.setOnClickListener {
			movie.isExpanded = !movie.isExpanded
			notifyItemChanged(position)
		}
	}

	override fun getItemCount(): Int {
		return movies.size
	}
}
