package hr.illuminative.mixscape.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import hr.illuminative.mixscape.model.Cocktail
import hr.illuminative.mixscape.ui.theme.spacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CocktailDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onSave: (Cocktail) -> Unit,
) {
    if (isVisible) {
        Dialog(
            onDismissRequest = { onDismiss() },
        ) {
            Surface(
                modifier = Modifier.wrapContentHeight(),
                shape = RoundedCornerShape(MaterialTheme.spacing.medium),
                color = MaterialTheme.colorScheme.primary,
            ) {
                Column(
                    modifier = Modifier.padding(MaterialTheme.spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium),
                ) {
                    Text("Add Your Cocktail")

                    var cocktailName by remember { mutableStateOf("") }
                    var ingredients by remember { mutableStateOf("") }
                    var preparationInstructions by remember { mutableStateOf("") }
                    var imageUrl by remember { mutableStateOf("") }

                    var isSaveEnabled by remember { mutableStateOf(false) }

                    fun validateFields() {
                        isSaveEnabled = (
                            cocktailName.isNotBlank() &&
                                ingredients.isNotBlank() &&
                                preparationInstructions.isNotBlank() &&
                                imageUrl.isNotBlank()
                            )
                    }

                    val fields = listOf(
                        Triple(cocktailName, "Cocktail Name", { value: String -> cocktailName = value }),
                        Triple(ingredients, "Ingredients", { value: String -> ingredients = value }),
                        Triple(preparationInstructions, "Preparation instructions", { value: String -> preparationInstructions = value }),
                        Triple(imageUrl, "Image URL", { value: String -> imageUrl = value }),
                    )

                    FlowRow(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            //.weight(1f),
                    ) {
                        for ((value, label, onValueChange) in fields) {
                            TextField(
                                value = value,
                                onValueChange = {
                                    onValueChange(it)
                                    validateFields()
                                },
                                label = { Text(label) },
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Button(onClick = { onDismiss() }, enabled = true) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = {
                                val cocktail = Cocktail(
                                    id = 0,
                                    name = cocktailName,
                                    ingredients = listOf(ingredients),
                                    preparationInstructions = preparationInstructions,
                                    imageUrl = imageUrl,
                                    isFavorite = false,
                                )
                                onSave(cocktail)
                                onDismiss()
                            },
                            enabled = isSaveEnabled,
                        ) {
                            Text("Save")
                        }
                    }
                }
            }
        }
    }
}
