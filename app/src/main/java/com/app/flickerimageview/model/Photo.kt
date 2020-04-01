package com.app.flickerimageview.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.flickerimageview.utils.Constants.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Photo(
    val farm: Int,
    @PrimaryKey
    val id: String,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String
): ListItem