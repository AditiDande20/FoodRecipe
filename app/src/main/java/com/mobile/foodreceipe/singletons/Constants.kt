package com.mobile.foodreceipe.singletons

class Constants {

    companion object{

        const val BASE_URL="https://api.spoonacular.com"
        const val API_KEY="7ad7b776140941849d40898580790b87"
        const val INGREDIENT_BASE_URL="https://spoonacular.com/cdn/ingredients_100x100/"

        const val DATABASE_NAME="recipe_database"
        const val RECIPE_TABLE="recipe_table"
        const val FAVORITE_RECIPE_TABLE="favorite_recipe_table"
        const val FOOD_JOKE_TABLE="food_joke_table"

        const val QUERY_SEARCH="query"
        const val QUERY_NUMBER="number"
        const val QUERY_API_KEY="apiKey"
        const val QUERY_TYPE="type"
        const val QUERY_DIET="diet"
        const val QUERY_RECIPE_INFORMATION="addRecipeInformation"
        const val QUERY_FILL_INGRIDIENTS="fillIngredients"

        //Bottom Sheet
        const val DEFAULT_NUMBER="50"
        const val DEFAULT_MEAL_TYPE="main course"
        const val DEFAULT_DIET_TYPE="gluten free"

        const val PREFERENCES_MEAL_TYPE="mealType"
        const val PREFERENCES_MEAL_TYPE_ID="mealTypeID"
        const val PREFERENCES_DIET_TYPE="dietType"
        const val PREFERENCES_DIET_TYPE_ID="dietTypeID"
        const val PREFERENCES_NAME="food_preference"

        const val BACK_ONLINE="backOnline"
        const val RECIPE_RESULT="recipeBundle"



    }

}