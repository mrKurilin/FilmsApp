package com.mrkurilin.filmsapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mrkurilin.filmsapp.data.room.model.FilmDetailsLocal
import com.mrkurilin.filmsapp.data.room.model.FilmStatusLocal

@Database(
    entities = [FilmDetailsLocal::class, FilmStatusLocal::class], version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filmLocalDao(): FilmLocalDao
}