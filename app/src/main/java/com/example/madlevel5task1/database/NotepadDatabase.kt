package com.example.madlevel5task1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.madlevel5task1.model.Note


@Database(entities = [Note::class],version = 1,exportSchema = false)
abstract class NotepadDatabase:RoomDatabase(){
    abstract fun noteDao(): NoteDao

    companion object{
        private const val DATABASE_NAME = "NOTEPAD_DATABASE"

        @Volatile
        private var INSTANCE:NotepadDatabase? = null


        fun getDatabase(context: Context): NotepadDatabase? {
            if (INSTANCE == null) {
                synchronized(NotepadDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            NotepadDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }

    }
}