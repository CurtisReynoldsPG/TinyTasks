package com.example.to_do

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun MainMenu.LoadObjects() {
    val myUID = auth.currentUser!!.uid

    var duplicate = false
    database.collection(myUID).get().addOnSuccessListener {
        it.forEach {

            var name = it.get("name").toString();
            var completed: Boolean = false;
            //TODO fix this garbage
            if(it.get("completed") == false)
            {
                completed = false
            }
            else
            {
                completed= true
            }

            var item: Items = Items(name, completed)
            AppData.items.forEach { oldItem ->
                if (oldItem.name == item.name)
                {
                    duplicate = true;
                }
            }
            if(!duplicate)
            {
                CoroutineScope(Dispatchers.IO).launch {
                    itemsDatabase!!.ItemsDao().insert(item)
                    AppData.items =  itemsDatabase!!.ItemsDao().getallItems().toMutableList()
                    withContext(Dispatchers.Main) {
                        createView()
                    }
                }
            }
            duplicate = false

        }
    }
}