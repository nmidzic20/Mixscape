package hr.illuminative.mixscape.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbFavoriteCocktail::class], version = 1, exportSchema = false)
abstract class MixscapeDatabase : RoomDatabase() {
    abstract fun favoriteCocktailDao(): FavoriteCocktailDAO
}
