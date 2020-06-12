package cn.enaium.epsilon.ui.elements

import cn.enaium.epsilon.ui.Color
import cn.enaium.epsilon.utils.FontUtils
import cn.enaium.epsilon.utils.Render2DUtils
import net.minecraft.client.util.math.MatrixStack

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
open class Button : Element {
    private var title: String
    private var icon: String? = null

    constructor(x: Int, y: Int, width: Int, height: Int, title: String) : super(x, y, width, height) {
        this.title = title
    }

    constructor(x: Int, y: Int, title: String) : this(x, y, 100, 20, title)

    constructor(x: Int, y: Int, width: Int, height: Int, title: String, icon: String) : this(
        x,
        y,
        width,
        height,
        title
    ) {
        this.icon = icon
    }

    constructor(x: Int, y: Int, title: String, icon: String) : this(x, y, 100, 20, title, icon)

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        this.hovered = Render2DUtils.isHovered(mouseX, mouseY, x, y, width, height)
        Render2DUtils.drawRectWH(
            matrices,
            x,
            y,
            width,
            height,
            if (this.hovered) Color.Button.hovered else Color.Button.background
        )
        FontUtils.drawHVCenteredWithShadowString(matrices, title, x + width / 2, y + height / 2, Color.title)
        if (icon != null) {
            val iconSize = height.toDouble()
            Render2DUtils.drawImage(icon!!, x.toDouble(), y.toDouble(), iconSize, iconSize)
        }
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        when (button) {
            0 -> {
                onLeftClicked()
            }
            1 -> {
                onRightClicked()
            }
            2 -> {
                onMiddleClicked()
            }
        }
        return super.mouseClicked(mouseX, mouseY, button)
    }

    open fun onLeftClicked() {

    }

    open fun onRightClicked() {

    }

    open fun onMiddleClicked() {

    }
}