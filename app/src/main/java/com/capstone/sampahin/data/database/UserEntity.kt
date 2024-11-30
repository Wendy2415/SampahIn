package com.capstone.sampahin.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "uid") val uid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "email") val email: String
)
