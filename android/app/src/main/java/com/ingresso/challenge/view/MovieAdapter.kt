package com.ingresso.challenge.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ingresso.challenge.R
import com.ingresso.challenge.model.MovieModel
import com.squareup.picasso.Picasso

// Estende RecyclerView.Adapter permitindo que ele faça data binding na RecyclerView do filme
class MovieAdapter(private val movies: List<MovieModel>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    // guarda referências para titulo e poster de cada filme
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.movie_title)
        val poster: ImageView = itemView.findViewById(R.id.movie_poster)
    }

    // Infla o item_movie.xml para cada filme e retorna um Holder para fácil acesso aos campos do filme
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    // Faz binding dos dados da API às views contidas no MovieViewHolder
    // Em essência, seta título e poster com o que vem da API
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title

        movie.images.firstOrNull()?.url?.let { url ->
            if (url.isNotBlank()) {
                Picasso.get().load(url).into(holder.poster)
            } else {
                holder.poster.setImageResource(R.drawable.ic_cinema_roll)
            }
        } ?: holder.poster.setImageResource(R.drawable.ic_cinema_roll)
    }


    override fun getItemCount() = movies.size
}
