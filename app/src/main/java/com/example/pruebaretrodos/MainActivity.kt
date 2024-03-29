package com.example.pruebaretrodos

import RetrofitServiceFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private var currentPage = 1
    private var isLoading = false
    private val moviesAdapter by lazy { MovieAdapter(mutableListOf()) } // Usa una MutableList para permitir la adición de elementos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler = findViewById(R.id.recycler_peliculas)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = moviesAdapter
        setupScrollListener()

        loadMovies(currentPage)
    }

    private fun setupScrollListener() {
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && totalItemCount <= (lastVisibleItem + 5)) {
                    loadMovies(++currentPage)
                    isLoading = true
                }
            }
        })
    }

    private fun loadMovies(page: Int) {
        val service = RetrofitServiceFactory.makeRetrofitService()
        lifecycleScope.launch {
            try {
                isLoading = true
                val newMovies = service.listPoupularMovies("3e5f06a6270e357ec14bb1a776288b2f", "es-ES", page)
                moviesAdapter.addMovies(newMovies.results)
                isLoading = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
