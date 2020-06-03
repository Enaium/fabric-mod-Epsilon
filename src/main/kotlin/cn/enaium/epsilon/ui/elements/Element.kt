package cn.enaium.epsilon.ui.elements

import net.minecraft.client.gui.AbstractParentElement
import net.minecraft.client.gui.Element
import net.minecraft.client.gui.widget.AbstractButtonWidget
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
abstract class Element(val tempX: Int,val tempY: Int,val width: Int,val height: Int): Element {
    val WIDGETS_LOCATION = Identifier("textures/gui/widgets.png")
    val id = 0
    var hovered = false
    val visible = true
    val enabled = true
    var scrollOffsetX = 0
    var scrollOffsetY = 0
    val x get() = tempX + scrollOffsetX
    val y get() = tempY + scrollOffsetY

    open fun tick() {

    }

    abstract fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float)

}