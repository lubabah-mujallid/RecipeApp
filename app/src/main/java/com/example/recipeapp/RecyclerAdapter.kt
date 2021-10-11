package com.example.recipeapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_layout.view.*

class RecyclerAdapter (val context: Context, val messages: ArrayList<Recipe.RecipeDetails>):
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        return RecyclerAdapter.ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.recycler_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val message = messages[position]
        holder.itemView.apply {
            tvRecipeTitle.text = message.title.toString()
            tvAuthor.text = message.author.toString()
            var ing = message.ingredients.toString()
            var ins = message.instructions.toString()
            cvCard.setOnClickListener { AlertDialog(context, tvRecipeTitle.text.toString(), ing, ins) }

        }
    }

    override fun getItemCount(): Int = messages.size


}
