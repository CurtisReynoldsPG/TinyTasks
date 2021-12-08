package com.example.to_do

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun MainMenu.WriteObject(newItem: Items) {

    val myUID = auth.currentUser!!.uid

    val ItemMap = newItem.toMap()
    val hashCode = newItem.name.hashCode().toString()
    database.collection(myUID).document(hashCode).set(ItemMap)
}
