package no.uio.ifi.in2000.sanderas.oblig1
import kotlin.math.roundToInt
class Converter {
    fun converter(number:Int , unit:ConverterUnits):Int{

        return when (unit){
            ConverterUnits.OUNCE -> (0.02957*number).roundToInt()
            ConverterUnits.CUP -> (0.23659*number).roundToInt()
            ConverterUnits.GALLON ->  (3.78541*number).roundToInt()
            ConverterUnits.HOGSHEAD ->  (238.481*number).roundToInt()
            else -> -1
        }
    }
}