package com.mrkurilin.filmsapp.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {

    private val listType = object : TypeToken<List<String?>?>() {}.type

    @TypeConverter
    fun fromString(value: String?): List<String> {
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}