package no.uio.ifi.in2000.sanderas.oblig1

import android.os.Bundle
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.sanderas.oblig1.ui.palindrome.PalindromeScreen
import no.uio.ifi.in2000.sanderas.oblig1.ui.unitconverter.UnitConverterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Sanderas_oblig1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "Palindrome") {
                        composable("Palindrome") { PalindromeScreen(navController) }
                        composable("Converter") { UnitConverterScreen(navController) }
                        // Add more destinations similarly.
                    }

                }

                }

            }
            """setContent {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //palindromeUI()

                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //converterUI()
                }
            }"""

        }
    }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun converterUI(){
    var tallinput by remember { mutableStateOf("") }


    val options = listOf("OUNCE", "CUP", "GALLON", "HOGSHEAD")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    var result by remember { mutableIntStateOf(0) }
    var feilmelding by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current

    Column (
        modifier = Modifier.fillMaxSize(),

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

}


@Composable
fun palindromeUI(){
    var svar by remember { mutableStateOf("") }
    var input by remember { mutableStateOf("") }
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){


        TextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Palindrom") },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    svar = isPalindrome(input).toString()
                }
            )
        )

        Button(onClick = { svar = isPalindrome(input).toString() }) {
            Text("sjekk")
        }

        val output = if (svar == "true"){
            "dette er et palindrom"
        }
        else {
            "dette er ikke et palindrom"
        }
        Text("$output")
    }
}
@Composable
fun palindromeInput(){
    var text by remember { mutableStateOf("palindrome") }

    TextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Label") }
    )

}



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Sanderas_oblig1Theme {
        Greeting("Android")
    }
}

fun isPalindrome(tekst:String):Boolean{
    var palindrome = Palindrome()
    return palindrome.isPalindrome(tekst)
}

fun stringTilUnit(String : String) : ConverterUnits {
    return when (String) {
        "OUNCE" -> ConverterUnits.OUNCE
        "CUP" -> ConverterUnits.CUP
        "GALLON" -> ConverterUnits.GALLON
        "HOGSHEAD" -> ConverterUnits.HOGSHEAD

        else -> {
            return ConverterUnits.OUNCE
        }
    }
}
fun converter(int:Int, unit: ConverterUnits):Int{
    val Converter = Converter()
    return Converter.converter(int, unit)
}