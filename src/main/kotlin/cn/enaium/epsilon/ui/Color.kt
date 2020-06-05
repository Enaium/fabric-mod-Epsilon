package cn.enaium.epsilon.ui

import java.awt.Color

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
object Color {

    val title = Color.WHITE.rgb

    object Button {
        val background = Color(192, 216, 217).rgb
        val hovered = Color(192, 216, 200).rgb
    }

    object CheckBox {
        val background = Color(192, 216, 217).rgb
        val disable = Color(255, 0, 0).rgb
        val enable = Color(0, 255, 0).rgb
    }

    object TextField {
        val background = Color(192, 216, 217).rgb
        val cursor = Color.BLACK.rgb
        val getFocus = Color.WHITE.rgb
        val lostFocus = Color.GRAY.rgb
    }

    object Label {
        val background = Color(192, 216, 217).rgb
    }
}