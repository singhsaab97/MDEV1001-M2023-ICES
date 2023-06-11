package com.kanwarabhijitsingh.ice5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoviesAdapter(private val movies: List<Movie>): RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_row, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val movie = movies[position]
		val titleView = holder.view.findViewById<TextView>(R.id.titleView)
		titleView.text = movie.title
		val studioView = holder.view.findViewById<TextView>(R.id.studioView)
		studioView.text = movie.studio
	}

	override fun getItemCount() = movies.size

	class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

}
