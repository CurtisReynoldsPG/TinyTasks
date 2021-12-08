package com.example.to_do

fun MainMenu.RemoveObject(newItem: Items) {

    val myUID = auth.currentUser!!.uid
    val hashCode = newItem.name.hashCode().toString()
    database.collection(myUID).document(hashCode).delete()
}
