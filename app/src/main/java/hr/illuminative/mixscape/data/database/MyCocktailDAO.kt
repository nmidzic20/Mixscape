package hr.illuminative.mixscape.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hr.illuminative.mixscape.model.Cocktail
import kotlinx.coroutines.flow.Flow

@Dao
interface MyCocktailDAO {
    @Query("SELECT * FROM my_cocktails")
    fun myCocktails(): Flow<List<DbMyCocktail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyCocktail(cocktail: DbMyCocktail)

    @Update
    suspend fun updateMyCocktail(cocktail: DbMyCocktail)

    @Query("DELETE FROM my_cocktails WHERE id = :cocktailId")
    suspend fun deleteMyCocktail(cocktailId: Int)
}