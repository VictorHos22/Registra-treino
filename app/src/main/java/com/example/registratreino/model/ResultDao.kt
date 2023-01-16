package com.example.registratreino.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ResultDao {

    @Insert
    fun insert(result: Result)

    @Query("SELECT * FROM Result WHERE type = :type")
    fun getRegisterByType(type: String): List<Result>
}