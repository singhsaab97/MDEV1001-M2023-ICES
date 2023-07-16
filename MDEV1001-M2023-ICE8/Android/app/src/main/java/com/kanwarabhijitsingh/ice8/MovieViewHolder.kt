package com.kanwarabhijitsingh.ice8

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

	private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
	private val studioTextView: TextView = itemView.findViewById(R.id.studioTextView)

	fun bind(movie: Movie) {
		titleTextView.text = movie.title
		studioTextView.text = movie.studio
	}
}
