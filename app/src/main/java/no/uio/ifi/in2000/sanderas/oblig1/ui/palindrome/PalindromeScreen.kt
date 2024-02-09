@file:OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeUiApi::class)

package no.uio.ifi.in2000.sanderas.oblig1.ui.palindrome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import no.uio.ifi.in2000.sanderas.oblig1.isPalindrome


@Composable
fun PalindromeScreen(navController: NavHostController) {
    var svar by remember { mutableStateOf("") }
    var input by remember { mutableStateOf("") }

    val keyboardController = LocalSoftwareKeyboardController.current
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
                    keyboardController?.hide()
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
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(onClick = { navController.navigate("Converter")}) {
            Text(text = "Til Unit Converteren")
        }
    }
}