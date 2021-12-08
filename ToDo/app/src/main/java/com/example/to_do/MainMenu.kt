package com.example.to_do

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainMenu : AppCompatActivity(), OnItemClickListener, FragmentClickListener{

    var itemsAdapter: ItemAdapter? = null
    var itemsDatabase: ItemDataBase? = null


    lateinit var auth: FirebaseAuth
    val database = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        //gets auth instance
        auth = FirebaseAuth.getInstance()

        findViewById<FloatingActionButton>(R.id.addButton).setOnClickListener(){createNewButton();}

        //creates fragment for logging in / out
        val internetButton = findViewById<ImageView>(R.id.online)
        internetButton.setOnClickListener()
        {
            if( supportFragmentManager.findFragmentByTag("login") == null)
            {
                if(!AppData.connected){
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fragmentContainer,
                            LoginFragment.newInstance(),
                            "login").commit()
                }else{
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.fragmentContainer,
                            LogOutFragment.newInstance(),
                            "login").commit()
                }
            }
        }

        //only tries to log in if password and email are not empty
        if(AppData.PASS != "" && AppData.EMAIL != "")
        {
            register()
        }

        //gets local database data
        itemsDatabase = ItemDataBase.getDatabase(this)!!
        CoroutineScope(Dispatchers.IO).launch {
            AppData.items = itemsDatabase!!.ItemsDao().getallItems().toMutableList()
            createView()
        }
    }

    //creates new items
    fun createNewButton() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("New Item")
        builder.setMessage("Please enter item name")

        val myInput = EditText(this)
        myInput.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(myInput)

        builder.setPositiveButton("Save")
        { dialogue, which ->
            val itemName: String = myInput.text.toString()
            if(itemName.contains(Regex("[<>'/;`%\\(\\)_+=\\[\\]\\{\\}|.,?:]")))
            {
                Toast.makeText(this, "Invalid Characters",
                    Toast.LENGTH_LONG).show()
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    val item: Items = Items(itemName, false  )
                    if(AppData.connected){
                        WriteObject(item)
                    }
                    itemsDatabase!!.ItemsDao().insert(item)
                    AppData.items =  itemsDatabase!!.ItemsDao().getallItems().toMutableList()
                    withContext(Dispatchers.Main) {
                        createView()
                    }
                }
            }
        }

        builder.setNegativeButton("Cancel")
        { dialogue, which ->

        }

        val dialogue: AlertDialog = builder.create()
        dialogue.show()
    }

    fun createView()
    {
        val myRv = findViewById<RecyclerView>(R.id.recyclerView)
        myRv.layoutManager = LinearLayoutManager(this)
        itemsAdapter = ItemAdapter (AppData.items.toList(), this)
        myRv.adapter = itemsAdapter
    }

    //used for clearing local database when account swap is detected
    fun clearAll()
    {
        CoroutineScope(Dispatchers.IO).launch {
            itemsDatabase!!.ItemsDao().nukeTable()
        }
        AppData.items.clear()
    }

    //item is tapped
    override fun itemClick(index: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            AppData.items[index].completed = !AppData.items[index].completed
            itemsDatabase!!.ItemsDao().updateItem(AppData.items[index])
            if(AppData.connected){
                WriteObject(AppData.items[index])
            }
        }
        itemsAdapter!!.notifyDataSetChanged()
    }

    //item is long clicked
    override fun itemLongClick(index: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            if(AppData.connected){
                RemoveObject(AppData.items[index])
            }
            itemsDatabase!!.ItemsDao().delete(AppData.items[index])
            AppData.items =  itemsDatabase!!.ItemsDao().getallItems().toMutableList()

            withContext(Dispatchers.Main) {
                createView()
            }
        }
    }

    //when player logs out removes password from local storage
    override fun onLogoutButton() {
        auth.signOut()
        with(AppData.sharedPref.edit()){
            putString("Password", "")
            AppData.connected = false;
            apply()
        }
        findViewById<ImageView>(R.id.online).setImageResource(R.drawable.noconnction)
        onCancelButton()
    }

    //removes fragment to return to main screen
    override fun onCancelButton() {
        val fragment = supportFragmentManager.findFragmentByTag("login")
        if(fragment != null)
        {
            supportFragmentManager
                .beginTransaction()
                .remove(fragment!!)
                .commit()
        }
    }

    //attempts to login or register
    override fun onLoginButton() {
        //Check if data
        register()
    }
}