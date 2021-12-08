package com.example.to_do

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Fade
import android.util.Log
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        AppData.sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        checkAccount()

        with(window){
            exitTransition = Fade()
            enterTransition = Fade()
        }
        changeScreens()
    }
    private fun changeScreens()
    {
        //val intent = Intent(this, MainMenu::class.java)
        val intent = Intent(this,
            MainMenu::class.java)

        startActivity(intent);
        //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    private fun checkAccount()
    {
        AppData.EMAIL = AppData.sharedPref.getString("Email", "")!!
        AppData.PASS = AppData.sharedPref.getString("Password", "")!!
    }

}