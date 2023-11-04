package hr.illuminative.mixscape.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbFavoriteCocktail::class], version = 1)
abstract class MixscapeDatabase : RoomDatabase() {
    abstract fun favoriteCocktailDao(): FavoriteCocktailDAO
}
