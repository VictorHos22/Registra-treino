package com.example.registratreino.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Result(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "type") val type:String,
    @ColumnInfo(name = "res") val res: String,
    @ColumnInfo(name = "created_date") val createdDate: Date = Date(),
)

