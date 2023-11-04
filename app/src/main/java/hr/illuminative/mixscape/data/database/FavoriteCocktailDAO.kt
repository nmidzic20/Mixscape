package hr.illuminative.mixscape.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCocktailDAO {
    @Query("SELECT * FROM favorite_cocktails")
    fun favorites(): Flow<List<DbFavoriteCocktail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCocktail(cocktail: DbFavoriteCocktail)

    @Query("DELETE FROM favorite_cocktails WHERE id = :cocktailId")
    suspend fun deleteFavoriteCocktail(cocktailId: Int)
}
