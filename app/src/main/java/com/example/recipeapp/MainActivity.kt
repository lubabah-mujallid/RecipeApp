package com.example.recipeapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var myList: ArrayList<Recipe.RecipeDetails>
    lateinit var addButton: FloatingActionButton
    lateinit var refreshButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton = floatingActionButton
        refreshButton = findViewById(R.id.refreshButton)
        myList = ArrayList()
        val adapter = RecyclerAdapter(this, myList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        requestAPI()

        addButton.setOnClickListener { addNew() }
        refreshButton.setOnClickListener { requestAPI() }
    }

    private fun requestAPI() {
        val progressDialog = ProgressDialog(this@MainActivity)
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                progressDialog.setMessage("Please wait")
                progressDialog.show()
            }
            Log.d("MAIN", "fetch data")
            async { fetchData() }.await()
            if (myList.isNotEmpty()) {
                Log.d("MAIN", "Successfully got all data")
                withContext(Dispatchers.Main){
                    recyclerView.smoothScrollToPosition(myList.size-1)}
            } else {
                Log.d("MAIN", "Unable to get data")
                //Toast.makeText(this@MainActivity, "Couldn't Refresh Data, Please Try Again!", Toast.LENGTH_LONG).show()
            }
            withContext(Dispatchers.Main){
                progressDialog.dismiss() }
        }
    }

    private suspend fun fetchData() {
        Log.d("MAIN", "went inside fetch")
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<List<Recipe.RecipeDetails>> = apiInterface!!.getRecipes()
        val response: Response<List<Recipe.RecipeDetails>>

        try {

            response = call.execute()
            withContext(Dispatchers.Main) {
                Log.d("MAIN", "fetch successful")
                for (Person in response.body()!!) {
                    updateTextView(Person)
                }
            }
        } catch (e: Exception) {
            Log.d("MAIN", "ISSUE: $e")
        }

    }

    private fun updateTextView(person: Recipe.RecipeDetails) {
        myList.add(person)
        recyclerView.adapter?.notifyDataSetChanged()

    }

    private fun addNew() {
        Log.d("MAIN", "going to new activity")
        intent = Intent(applicationContext, PostRecipe::class.java)
        startActivity(intent)
    }

}

/*
                myET.text.clear()
                myET.clearFocus()
                rvList.adapter?.notifyDataSetChanged()

 Create an application that makes use of the following API:

https://dojo-recipes.herokuapp.com/recipes/

Use the JSON data that comes back to populate a Recycler View with TextViews

Your app should also allow users to make POST Requests with the following fields:

Title
Author
Ingredients
Instructions

Once the user enters the data and clicks on the save button, the data should be added to the server todo

Once your application is complete, update the Recycler View to use Card Views to display the recipes

Each Card View should display the Title of the recipe and its Author

When users click on the Card View, they should be presented with the full Recipe todo (including Ingredients and Instructions)

This information should be displayed in a visually appealing manner (feel free to experiment with using a separate Activity, or showing the information in another way)
* */


/*
get
    retrieve items from recycler
post
    add item to recycler

- 2 activities, 1 class, 3 layout files
---------------------------------------
- 1 activity: recycler view prints class
    - add retrofit and coroutines todo
- 2 activity: read user input to class (post) todo
    - connect with layout todo
---------------------------------------
- row_item layout for recycler
- 1 layout with recycler view and + button
- 2 layout with 2 buttons and 2 edit text todo
* */