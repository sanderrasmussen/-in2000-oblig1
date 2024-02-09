@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)

package no.uio.ifi.in2000.sanderas.oblig1.ui.unitconverter
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import no.uio.ifi.in2000.sanderas.oblig1.ui.theme.Sanderas_oblig1Theme
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalSoftwareKeyboardController.current
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.webkit.Profile
import no.uio.ifi.in2000.sanderas.oblig1.converter
import no.uio.ifi.in2000.sanderas.oblig1.stringTilUnit
import no.uio.ifi.in2000.sanderas.oblig1.ui.palindrome.PalindromeScreen


@Composable
fun UnitConverterScreen(navController: NavHostController){
    var tallinput by remember { mutableStateOf("") }


    val options = listOf("OUNCE", "CUP", "GALLON", "HOGSHEAD")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    var result by remember { mutableIntStateOf(0) }
    var feilmelding by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){


        TextField(

            value = tallinput,
            onValueChange = { tallinput = it },
            label = { Text("") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.NumberPassword
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    try {
                        feilmelding = ""
                        result = converter(
                            tallinput.toInt(),
                            stringTilUnit(selectedOptionText)
                        )


                        keyboardController?.hide()
                        tallinput = ""
                    }
                    catch (nfe: NumberFormatException) {
                        feilmelding= "Tallet du skrev inn var for stort, skriv et mindre tall."
                        tallinput=""
                    }


                }
            )
        )



        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            TextField(
                // The `menuAnchor` modifier must be passed to the text field for correctness.
                modifier = Modifier.menuAnchor(),
                readOnly = true,
                value = selectedOptionText,
                onValueChange = {},
                label = { Text("Label") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }

        }
        Button(onClick = {
            try {
                feilmelding = ""
                result = converter(
                    tallinput.toInt(),
                    stringTilUnit(selectedOptionText)
                )
                keyboardController?.hide()
                tallinput = ""
            }
            catch (nfe: NumberFormatException) {
                feilmelding= "Tallet du skrev inn var for stort, skriv et mindre tall."
                tallinput=""
            }})
        { Text("converter") }

        Text("$result Liter")
        Text("$feilmelding")
        

    }
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = { navController.navigate("Palindrome")}) {
            Text(text = "Til Palindrome sjekkeren")
        }
    }

}


