package com.udacity.baking.net.contracts;

import com.udacity.baking.net.TO.RecipeTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeContract {

    @GET("baking.json")
    Call<List<RecipeTO>> getRecipes();
}
