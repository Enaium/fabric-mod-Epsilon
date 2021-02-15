package cn.enaium.epsilon.ui.elements

import cn.enaium.epsilon.ui.Color
import cn.enaium.epsilon.client.utils.FontUtils
import cn.enaium.epsilon.client.utils.Render2DUtils
import net.minecraft.client.util.math.MatrixStack

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
open class ModeButton : Element {

    private var mods: ArrayList<String>
    var current = 0

    constructor(x: Int, y: Int, width: Int, height: Int, mods: ArrayList<String>) : super(x, y, width, height) {
        this.mods = mods
    }

    constructor(x: Int, y: Int, width: Int, height: Int, mods: ArrayList<String>, current: Int) : this(
        x,
        y,
        width,
        height,
        mods
    ) {
        this.current = current
    }

    constructor(x: Int, y: Int, mods: ArrayList<String>) : this(x, y, 100, 20, mods)

    constructor(x: Int, y: Int, mods: ArrayList<String>, current: Int) : this(x, y, 100, 20, mods, current)

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
        FontUtils.drawHVCenteredWithShadowString(
            matrices,
            "($current/${mods.size - 1})${mods[current]}",
            x + width / 2,
            y + height / 2,
            Color.title
        )
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
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

}