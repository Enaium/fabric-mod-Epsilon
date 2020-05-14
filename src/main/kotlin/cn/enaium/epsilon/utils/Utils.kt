package cn.enaium.epsilon.utils

import java.text.DecimalFormat
import java.util.*


object Utils {

    fun valueFix(value: Float): Float {
        val decimalFormat = DecimalFormat(".0")
        return decimalFormat.format(value.toDouble()).toFloat()
    }

    fun valueFix(value: Double): Double {
        val decimalFormat = DecimalFormat(".0")
        return decimalFormat.format(value).toDouble()
    }

    fun random(min: Int, max: Int): Int {
        return Random().nextInt(max - min) + min
    }
}