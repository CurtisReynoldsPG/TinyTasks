package com.example.to_do

import androidx.room.*

@Dao
interface ItemDao
{
    @Insert
    fun insert(Items: Items)

    @Query("SELECT * FROM ItemsTable ORDER BY name ASC")
    fun getallItems(): List<Items>


    /**
     *   WE ONLY USE THE TWO ABOVE OTHERS ARE JUST EXAMPLES
     */
    @Query("SELECT * FROM ItemsTable")
    fun getAll(): List<Items>

    // find by name
    @Query("SELECT * FROM ItemsTable WHERE name LIKE :name")
    fun findByTitle(name: String): Items

    @Delete
    fun delete(todo: Items)

    @Update
    fun updateItem(vararg item: Items)

    @Query("DELETE FROM ItemsTable")
    fun nukeTable()
}
