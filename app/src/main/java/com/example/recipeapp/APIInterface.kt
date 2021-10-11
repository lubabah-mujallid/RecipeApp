package com.example.recipeapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body


interface APIInterface {

    @GET("recipes/")
    fun getRecipes(): Call<List<Recipe.RecipeDetails>>

    @POST("recipes/")
    fun addRecipe(@Body recipe : Recipe.RecipeDetails): Call<List<Recipe.RecipeDetails>>

}