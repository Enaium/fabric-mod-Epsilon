package cn.enaium.epsilon.client.utils

import cn.enaium.epsilon.client.cf4m
import net.minecraft.util.Formatting
import java.text.DecimalFormat
import java.util.*

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
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

    fun clearFormat(text: String): String {
        var string = text
        for (s in string.toCharArray()) {
            var i = 0
            while (i < string.length) {
                val c: Char = string[i]
                if (c.toInt() == 167 && i < text.length - 1) {
                    ++i
                    string = string.replace(Formatting.byCode(string[i]).toString(), "").replace("^[　 ]*", "").replace("[　 ]*$", "")
                }
                ++i
            }
        }
        return string
    }
}