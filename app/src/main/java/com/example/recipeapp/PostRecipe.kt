package com.example.recipeapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostRecipe : AppCompatActivity() {
    lateinit var etTitle: EditText
    lateinit var etAuthor: EditText
    lateinit var etIng: EditText
    lateinit var etIns: EditText
    lateinit var addButton: Button
    lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_recipe)
        etTitle = findViewById(R.id.etTitle)
        etAuthor = findViewById(R.id.etAuthor)
        etIng = findViewById(R.id.etIngredients)
        etIns = findViewById(R.id.etInstructions)

        addButton = findViewById(R.id.buttonSave)
        cancelButton = findViewById(R.id.buttonCancel)

        addButton.setOnClickListener {
            var recipe = Recipe.RecipeDetails(etTitle.text.toString(),etAuthor.text.toString(),etIng.text.toString(),etIns.text.toString())
            Log.d("POST", "recipe is: $recipe")
            newRecipe(recipe)
            etTitle.setText("");etAuthor.setText("")
            etIng.setText("");etIns.setText("")
        }
        cancelButton.setOnClickListener { cancelAddition() }
    }

    fun newRecipe(person: Recipe.RecipeDetails) {
        val progressDialog = ProgressDialog(this)
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                progressDialog.setMessage("Please wait")
                progressDialog.show()
            }
            Log.d("POST", "fetch data")
            async {
                val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
                val call: Call<List<Recipe.RecipeDetails>> = apiInterface!!.addRecipe(person)
                val response: Response<List<Recipe.RecipeDetails>>
                try {
                    response = call.execute()
                    Log.d("POST", "fetch successful")
                }
                catch (e: Exception){ Log.d("POST", "ISSUE: $e") }
            }.await()
            withContext(Dispatchers.Main){
                progressDialog.dismiss() }
        }
    }

    fun addPerson(person: Recipe.RecipeDetails) {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait")
        progressDialog.show()

        if (apiInterface != null) {
            Log.d("MAIN", "Post interface not null")
            apiInterface.addRecipe(person).enqueue(object : Callback<List<Recipe.RecipeDetails>> {
                override fun onResponse(call: Call<List<Recipe.RecipeDetails>>, response: Response<List<Recipe.RecipeDetails>>) {
                    Log.d("MAIN", "adding success")
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<List<Recipe.RecipeDetails>>, t: Throwable) {
                    Log.d("MAIN", " adding failure" + "ISSUE: ")
                    progressDialog.dismiss()
                }
            })
        }
        else{ Log.d("MAIN", "Post interface null") }
    }


    private fun cancelAddition() {
        intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}