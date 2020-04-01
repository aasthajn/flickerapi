package com.app.flickerimageview.db

import androidx.room.*
import com.app.flickerimageview.model.Photo
import com.app.flickerimageview.utils.Constants.Companion.DELETE_TABLE
import com.app.flickerimageview.utils.Constants.Companion.TABLE_NAME

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: Photo)

    @Query("SELECT * FROM $TABLE_NAME")
    fun getImages(): List<Photo>

    @Query(DELETE_TABLE)
    suspend fun deleteAll()

    @Delete
    suspend fun delete(image: Photo)

}