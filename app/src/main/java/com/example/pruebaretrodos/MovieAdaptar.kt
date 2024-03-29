package com.example.pruebaretrodos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import data.model.Result
import java.util.Locale

class MovieAdapter(private var movies: MutableList<Result>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(), Filterable {

    var moviesFiltered = mutableListOf<Result>()
    // Variable para almacenar el último texto de búsqueda.
    private var lastSearch = ""

    init {
        moviesFiltered = movies
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val movieImage: ImageView = view.findViewById(R.id.movie_image)
        val movieTitle: TextView = view.findViewById(R.id.movie_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = moviesFiltered[position]
        holder.movieTitle.text = movie.title
        Glide.with(holder.movieImage.context).load("https://image.tmdb.org/t/p/w500${movie.poster_path}").into(holder.movieImage)
    }

    fun addMovies(newMovies: List<Result>) {
        movies.addAll(newMovies)
        // Refiltrar las películas basado en el último texto de búsqueda.
        filter.filter(lastSearch)
    }

    override fun getItemCount(): Int = moviesFiltered.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                lastSearch = charString // Actualizar el último texto de búsqueda.

                moviesFiltered = if (charString.isEmpty()) {
                    movies
                } else {
                    val filteredList = mutableListOf<Result>()
                    for (movie in movies) {
                        if (movie.title.lowercase(locale = Locale.ROOT).contains(
                                charString.lowercase(
                                    Locale.ROOT
                                )
                            )) {
                            filteredList.add(movie)
                        }
                    }
                    filteredList
                }
                return FilterResults().apply { values = moviesFiltered }
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                moviesFiltered = results?.values as MutableList<Result>
                notifyDataSetChanged()
            }
        }
    }
}
