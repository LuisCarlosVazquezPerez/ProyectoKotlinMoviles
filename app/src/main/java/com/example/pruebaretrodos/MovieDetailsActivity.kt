package com.example.pruebaretrodos

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class MovieDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        // Recuperar datos del Intent
        val title = intent.getStringExtra("title") ?: "Título no disponible"
        val imageUrl = intent.getStringExtra("image_url") ?: ""
        val description = intent.getStringExtra("description") ?: "Descripción no disponible"
        val backdropPath = intent.getStringExtra("backdrop_path") ?: "" // Asegúrate de pasar este extra correctamente

        // Encontrar vistas por ID
        val movieBackdrop: ImageView = findViewById(R.id.movieBackdrop) // El ImageView para el fondo
        val movieImage: ImageView = findViewById(R.id.movieImage)
        val movieTitle: TextView = findViewById(R.id.movieTitle)
        val movieDescription: TextView = findViewById(R.id.movieDescription)

        // Poblar vistas con los datos
        movieTitle.text = title
        movieDescription.text = description
        Glide.with(this).load(imageUrl).into(movieImage) // Usando Glide para cargar la imagen de la película
        Glide.with(this).load("https://image.tmdb.org/t/p/original$backdropPath").into(movieBackdrop) // Cargando la imagen de fondo
    }
}
