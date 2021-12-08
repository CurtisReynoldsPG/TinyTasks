package com.example.to_do

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

fun MainMenu.WriteAllObjects() {

    val myUID = auth.currentUser!!.uid

    for(each in AppData.items)
    {
        val ItemMap = each.toMap()
        val hashCode = each.name.hashCode().toString()
        database.collection(myUID).document(hashCode).set(ItemMap)
    }
}
