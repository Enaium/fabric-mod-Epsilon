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
open class CheckBox : Element {
    var title: String
    var checked = false

    constructor(x: Int, y: Int, width: Int, height: Int, title: String) : super(x, y, width, height) {
        this.title = title
    }

    constructor(x: Int, y: Int, width: Int, height: Int, title: String, checked: Boolean) : this(
        x,
        y,
        width,
        height,
        title
    ) {
        this.checked = checked
    }

    constructor(x: Int, y: Int, title: String) : this(x, y, 100, 20, title)

    constructor(x: Int, y: Int, title: String, checked: Boolean) : this(x, y, 100, 20, title, checked)

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        val boxSize = height - 4
        hovered = Render2DUtils.isHovered(mouseX, mouseY, x - 2, y - 2, boxSize, boxSize)
        Render2DUtils.drawRectWH(matrices, x, y, width, height, Color.CheckBox.background)
        Render2DUtils.drawRectWH(
            matrices,
            x + 2,
            y + 2,
            boxSize,
            boxSize,
            if (this.checked) Color.CheckBox.enable else Color.CheckBox.disable
        )
        FontUtils.drawHVCenteredWithShadowString(matrices, title, x + width / 2, y + height / 2, Color.title)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        this.checked = !this.checked
        if (checked) onEnable() else onDisable()
        if (button == 0) {
            onLeftClicked()
        } else if (button == 1) {
            onRightClicked()
        }
        return super.mouseClicked(mouseX, mouseY, button)
    }

    open fun onLeftClicked() {

    }

    open fun onRightClicked() {

    }

    open fun onEnable() {

    }

    open fun onDisable() {

    }
}