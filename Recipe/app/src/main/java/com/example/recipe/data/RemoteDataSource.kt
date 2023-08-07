package com.example.recipe.data

import com.example.recipe.data.network.FoodRecipeAPI
import com.example.recipe.models.FoodRecipe
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Response
import javax.inject.Inject

@ActivityRetainedScoped
class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipeAPI
) {

    fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }
}