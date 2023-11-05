package hr.illuminative.mixscape.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_cocktails")
data class DbMyCocktail(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "ingredients") val ingredients: String,
    @ColumnInfo(name = "preparationInstructions") val preparationInstructions: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
)