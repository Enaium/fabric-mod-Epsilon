package cn.enaium.epsilon.client.ui.elements

import net.minecraft.client.gui.Element
import net.minecraft.client.util.math.MatrixStack

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
abstract class Element(val tempX: Int, val tempY: Int, val width: Int, val height: Int) : Element {
    val id = 0
    var hovered = false
    var visible = true
    val enabled = true
    private var focused = false
    var scrollOffsetX = 0
    var scrollOffsetY = 0
    val x get() = tempX + scrollOffsetX
    val y get() = tempY + scrollOffsetY

    open fun tick() {

    }

    abstract fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float)

    open fun isFocused(): Boolean {
        return focused
    }

    open fun setFocused(focused: Boolean) {
        this.focused = focused
    }

}