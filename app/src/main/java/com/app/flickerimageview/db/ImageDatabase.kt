package com.app.movieapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.flickerimageview.db.ImageDao
import com.app.flickerimageview.model.Photo
import com.app.flickerimageview.model.SearchItem

@Database(entities = [Photo::class], version = 1, exportSchema = false)
abstract class ImageDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    companion object {

        private var INSTANCE: ImageDatabase?=null

        fun getDatabase(context: Context): ImageDatabase? {
            if (INSTANCE == null) {
                synchronized(ImageDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ImageDatabase::class.java, "image_database"
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}