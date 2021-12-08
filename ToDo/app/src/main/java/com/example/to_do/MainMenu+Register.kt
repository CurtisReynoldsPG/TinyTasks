package com.example.to_do

import android.util.Log
import android.widget.ImageView
import android.widget.Toast

fun MainMenu.register()
{
    auth.signInWithEmailAndPassword(AppData.EMAIL,
        AppData.PASS)
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful)
            {
                val user = auth.currentUser
                Toast.makeText(this,
                    "logged in",
                    Toast.LENGTH_LONG)
                    .show()
                findViewById<ImageView>(R.id.online).setImageResource(R.drawable.connected)
                onCancelButton();
                if(AppData.sharedPref.getString("Email", "") != AppData.EMAIL) clearAll()
                with(AppData.sharedPref.edit()){
                    putString("Password",AppData.PASS)
                    putString("Email",AppData.EMAIL)
                    AppData.connected = true;
                    LoadObjects()
                    WriteAllObjects()
                    apply()
                }
            }
            else
            {
                // If sign in fails, try registering.
                auth.createUserWithEmailAndPassword(AppData.EMAIL,
                    AppData.PASS)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful)
                        {
                            // if successful, we can change the name
                            register()
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this,
                                task.exception.toString(),
                                Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            }

        }
}