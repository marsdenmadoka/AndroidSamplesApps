package com.sleepTrackerWihtRecyclerview.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface roomDao {

    //method for inserting a single sleepNight
    @Insert
    fun insert(night:TableEntity)

    //Method for updating a single sleepNight
    @Update
    fun update(night:TableEntity)

    //get() method that gets the sleepNight by key
    @Query("SELECT * FROM daily_sleep_quality_table WHERE nightId = :key")
    fun get(key:Long):TableEntity?

    //clear() clear the database by deleting all rows without deleting the table
    @Query("DELETE FROM daily_sleep_quality_table")
    fun clear()

    //getAllNights() returning all the rows in the table
    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
    fun getAllNights(): LiveData<List<TableEntity>>

    /**
     * returns the most recent night then order them by order and returning only one indicated by LIMIT1
     * since we sort by nightId its the one with highest id which is the latest night
     */
    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTonight():TableEntity? //nullable because when we clear all night content there is no night


    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    fun getNightWithId(key: Long): LiveData<TableEntity>
}