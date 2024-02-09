package no.uio.ifi.in2000.sanderas.oblig1

import androidx.compose.ui.text.toLowerCase

class Palindrome {
    fun isPalindrome(tekst:String):Boolean{
        val tekst = tekst.lowercase()
        return tekst.reversed() == tekst
    }
}