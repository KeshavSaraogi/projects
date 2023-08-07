package com.example.recipe.data.network

import com.example.recipe.models.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipeAPI {

    @GET("/recipes/complexSearch")
    fun getRecipes(
        @QueryMap queries: Map<String,String>
    ) : Response<FoodRecipe>
}