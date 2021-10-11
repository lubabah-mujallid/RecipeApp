package com.example.recipeapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class AlertDialog(context: Context, title: String, text1: String, text2: String) {
    init {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage("Ingredients: $text1 \nInstructions: $text2")
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert = dialogBuilder.create()
        alert.setTitle(title)
        alert.show()
    }
}