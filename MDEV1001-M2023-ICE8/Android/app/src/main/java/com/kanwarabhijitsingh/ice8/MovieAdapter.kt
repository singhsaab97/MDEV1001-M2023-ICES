package com.kanwarabhijitsingh.ice8

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(private val movies: List<Movie>) :
	RecyclerView.Adapter<MovieViewHolder>() {

	private var editAction: ((Movie) -> Unit)? = null
	private var deleteAction: ((Movie) -> Unit)? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.view_holder_movie, parent, false)
		return MovieViewHolder(view)
	}

	override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
		val movie = movies[position]
		holder.bind(movie)
		val deleteImageView = holder.itemView.findViewById<ImageView>(R.id.deleteIcon)
		// Delete action
		deleteImageView.setOnClickListener {
			deleteAction?.invoke(movie)
		}
		// Edit action
		holder.itemView.setOnClickListener {
			editAction?.invoke(movie)
		}
	}

	override fun getItemCount(): Int {
		return movies.size
	}

	fun setDeleteActionListener(listener: (Movie) -> Unit) {
		deleteAction = listener
	}

	fun setEditActionListener(listener: (Movie) -> Unit) {
		editAction = listener
	}
}
