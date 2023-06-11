package com.kanwarabhijitsingh.ice5

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoviesAdapter(private val movies: List<Movie>): RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_row, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val movie = movies[position]
		val titleTextView = holder.view.findViewById<TextView>(R.id.titleTextView)
		titleTextView.text = movie.title
		val studioTextView = holder.view.findViewById<TextView>(R.id.studioTextView)
		studioTextView.text = movie.studio
		val ratingTextView = holder.view.findViewById<TextView>(R.id.ratingTextView)
		ratingTextView.text = movie.criticsRating.toString()
		val ratingLayout = holder.view.findViewById<LinearLayout>(R.id.ratingLayout)
		ratingLayout.setBackgroundColor(movie.ratingColor)
	}

	override fun getItemCount() = movies.size

	class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

}
