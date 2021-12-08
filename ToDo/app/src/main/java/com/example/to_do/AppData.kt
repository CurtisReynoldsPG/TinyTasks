package com.example.to_do

import android.content.Context
import android.content.SharedPreferences

class AppData {
    companion object Data
    {
        lateinit var sharedPref :SharedPreferences

        var items: MutableList<Items> = mutableListOf()

        var EMAIL = ""
        //Note for the love of god do not store a password in raw text
        var PASS = ""

        var connected = false


        fun loadDummyData()
        {
            val item1 = Item ("press on a task to mark it as done", true)
            val item2 = Item ("long press on a task to delete it", false)
            val item3 = Item ("Add a new task by pressing the bottom right button", false)
            val item4 = Item ("return to the previous page with the top left back arrow", false)




        }
    }
}