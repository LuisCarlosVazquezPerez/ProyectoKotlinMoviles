package com.example.pruebaretrodos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import data.model.Result

class MovieAdapter(private val movies: MutableList<Result>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieImage: ImageView = view.findViewById(R.id.movie_image)
        val movieTitle: TextView = view.findViewById(R.id.movie_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.movieTitle.text = movie.title
        Glide.with(holder.movieImage.context)
            .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
            .into(holder.movieImage)
    }

    fun addMovies(newMovies: List<Result>) {
        movies.addAll(newMovies)
        notifyDataSetChanged() // Notifica a RecyclerView que los datos han cambiado
    }

    override fun getItemCount(): Int = movies.size
}
