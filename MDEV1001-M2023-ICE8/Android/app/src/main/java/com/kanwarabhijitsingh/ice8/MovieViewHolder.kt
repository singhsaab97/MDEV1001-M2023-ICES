package com.kanwarabhijitsingh.ice8

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
	private val studioTextView: TextView = itemView.findViewById(R.id.studioTextView)
	private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

	fun bind(movie: Movie) {
		titleTextView.text = movie.title
		studioTextView.text = movie.studio
		descriptionTextView.text = movie.description

		// Set visibility of description based on expanded state
		descriptionTextView.visibility =
			if (movie.isExpanded) View.VISIBLE else View.GONE
	}
}
