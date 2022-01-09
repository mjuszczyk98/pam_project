package com.mjuszczyk241379.project.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Password::class, SyncDate::class, Code::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun passwordDao(): PasswordDao
    abstract fun syncDateDao(): SyncDateDao
    abstract fun codeDao(): CodeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            var tmpInstance = INSTANCE
            if (tmpInstance != null) {
                return tmpInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "p_manager_database"
                ).fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}