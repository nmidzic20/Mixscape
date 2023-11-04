package hr.illuminative.mixscape.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cocktails")
data class DbFavoriteCocktail(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "poster_url") val posterUrl: String,
)