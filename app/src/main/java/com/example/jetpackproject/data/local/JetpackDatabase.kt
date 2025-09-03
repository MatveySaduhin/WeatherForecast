package com.example.jetpackproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class JetpackDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}