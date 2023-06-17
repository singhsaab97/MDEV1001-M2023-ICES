package com.kanwarabhijitsingh.ice6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoviesAdapter: RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

	private var movies = mutableListOf<Movie>()
	private var editAction: ((Movie) -> Unit)? = null
	private var deleteAction: ((Movie) -> Unit)? = null

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
		val deleteImageView = holder.view.findViewById<ImageView>(R.id.deleteImageView)
		// Delete action
		deleteImageView.setOnClickListener {
			deleteAction?.invoke(movie)
		}
		// Edit action
		holder.view.setOnClickListener {
			editAction?.invoke(movie)
		}
	}

	override fun getItemCount() = movies.size

	fun setDeleteActionListener(listener: (Movie) -> Unit) {
		deleteAction = listener
	}

	fun setEditActionListener(listener: (Movie) -> Unit) {
		editAction = listener
	}

	fun reset(updatedMovies: List<Movie>) {
		movies.apply {
			clear()
			addAll(LinkedHashSet<Movie>(updatedMovies).toMutableList())
		}
		notifyDataSetChanged()
	}

	class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

}
