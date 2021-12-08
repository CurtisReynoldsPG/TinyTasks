package com.example.to_do

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Item(val name: String,
                var completed: Boolean)

@Entity(tableName = "ItemsTable")
class Items(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "completed") var completed: Boolean) {
    @PrimaryKey(autoGenerate = true) var itemId: Long = 0

    fun toMap(): Map<String, Any?>
    {
        return mapOf("name" to name,
                     "completed" to completed)
    }
}