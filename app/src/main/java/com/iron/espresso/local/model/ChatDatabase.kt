package com.iron.espresso.local.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ChatEntity::class], version = 1)
abstract class ChatDatabase : RoomDatabase() {

    abstract fun chatDao(): ChatDao
}