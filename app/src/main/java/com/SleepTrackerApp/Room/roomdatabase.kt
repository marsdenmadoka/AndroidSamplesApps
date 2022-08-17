package com.SleepTrackerApp.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TableEntity::class], version = 1)
abstract class roomdatabase : RoomDatabase() {
    abstract val roomDao : roomDao

    companion object{
        @Volatile
        private var INSTANCE:roomdatabase? = null

        fun getInstance(context: Context): roomdatabase {
            synchronized(this){
                var instance  = INSTANCE

                if(instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    roomdatabase::class.java ,
                    "room_database"
                ) .fallbackToDestructiveMigration()
                    .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}