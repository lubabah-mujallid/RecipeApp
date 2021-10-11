package com.example.recipeapp

class Recipe {
    var data: List<RecipeDetails>? = null
    data class RecipeDetails(
        val title: String? = null,
        val author: String? = null,
        val ingredients: String? = null,
        val instructions: String? = null
    )
}