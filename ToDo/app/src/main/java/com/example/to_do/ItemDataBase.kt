package com.example.to_do

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Items::class],
    version = 2, exportSchema = false)
abstract class ItemDataBase : RoomDatabase()
{
    abstract fun ItemsDao(): ItemDao

    companion object
    {
        private var instance: ItemDataBase? = null

        fun getDatabase(context: Context): ItemDataBase?
        {
            if (instance == null)
            {

                synchronized(ItemDataBase::class)
                {
                    instance = Room.databaseBuilder(context.applicationContext,
                        ItemDataBase::class.java,
                        "group.db").build()
                }
            }
            return instance
        }
    }
}