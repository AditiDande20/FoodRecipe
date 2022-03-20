package com.mobile.foodreceipe.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.mobile.foodreceipe.R
import com.mobile.foodreceipe.adapters.PagerAdapter
import com.mobile.foodreceipe.entity.FavoritesRecipeEntity
import com.mobile.foodreceipe.fragments.IngredientsFragment
import com.mobile.foodreceipe.fragments.InstructionsFragment
import com.mobile.foodreceipe.fragments.OverviewFragment
import com.mobile.foodreceipe.singletons.Constants
import com.mobile.foodreceipe.viewmodels.FoodReceipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*
import java.lang.Exception

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val args by navArgs<DetailActivityArgs>()
    private val mainViewModel : FoodReceipeViewModel by viewModels()
    private var recipeSaved= false
    private var savedRecipeId= 0
    private lateinit var favMenu:MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(OverviewFragment())
        fragmentList.add(IngredientsFragment())
        fragmentList.add(InstructionsFragment())

        val titlesList = ArrayList<String>()
        titlesList.add("Overview")
        titlesList.add("Ingredients")
        titlesList.add("Instructions")

        val bundle = Bundle()
        bundle.putParcelable(Constants.RECIPE_RESULT, args.result)

        val adapter = PagerAdapter(bundle, fragmentList, titlesList, supportFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.detail_menu, menu)
        favMenu = menu?.findItem(R.id.save_to_favorite)!!
        checkedSavedRecipe(favMenu)


        return super.onCreateOptionsMenu(menu)
    }

    private fun checkedSavedRecipe(favMenu: MenuItem?) {

        mainViewModel.readFavoriteRecipes.observe(this,{favoritesEntity ->
            try {
                for( savedRecipe in favoritesEntity){
                    if(savedRecipe.result.id==args.result.id){
                        if (favMenu != null) {
                            changeMenuItemColor(favMenu,R.color.yellow)
                            savedRecipeId=savedRecipe.id
                            recipeSaved=true
                        }
                    }

                }

            }
            catch (e:Exception){

            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        if(item.itemId == R.id.save_to_favorite && !recipeSaved){
            saveToFavorites(item)
        }

        if(item.itemId == R.id.save_to_favorite && recipeSaved){
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorites(menuItem: MenuItem) {

        val favoritesRecipeEntity = FavoritesRecipeEntity (0,args.result)
        mainViewModel.insertFavoriteRecipe(favoritesRecipeEntity)
        changeMenuItemColor(menuItem,R.color.yellow)
        recipeSaved=true
        Snackbar.make(detailActivity,"Added to Favorites !!!",Snackbar.LENGTH_SHORT).setAction("OKAY"){}.show()
    }

    private fun changeMenuItemColor(menuItem: MenuItem,color:Int) {
        menuItem.icon.setTint(ContextCompat.getColor(this,color))
    }

    private fun removeFromFavorites(menuItem: MenuItem){

        val favoritesRecipeEntity=FavoritesRecipeEntity(savedRecipeId,args.result)
        mainViewModel.deleteFavoriteRecipe(favoritesRecipeEntity)
        changeMenuItemColor(menuItem,R.color.white)
        recipeSaved=false
        Snackbar.make(detailActivity,"Removed from Favorites !!!",Snackbar.LENGTH_SHORT).setAction("OKAY"){}.show()


    }

    override fun onDestroy() {
        super.onDestroy()
        changeMenuItemColor(favMenu,R.color.white)
    }

}