package cn.enaium.epsilon.screen.clickgui

import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.utils.ColorUtils
import cn.enaium.epsilon.utils.FontUtils
import cn.enaium.epsilon.utils.Render2DUtils
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class FuncPanel(var func: Func, var width: Double, var height: Double) {

    private var hovering = false

    fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float, x: Double, y: Double) {
        hovering = Render2DUtils.isHovered(mouseX.toDouble(), mouseY.toDouble(), x, y, width, height)
        var color = ColorUtils.ASPHALT
        if (func.enable) {
            color = ColorUtils.TURKIC
        }
        if (hovering) {
            color = ColorUtils.SELECT
        }
        Render2DUtils.drawRect(matrices, x, y, x + width, y + height, color)
        FontUtils.drawHVCenteredString(matrices, func.name, (x + (x + width)) / 2, (y + (y + height)) / 2, 0xFFFFFF)
    }

    fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {
        if (hovering && button == 0) {
            func.enable()
        }
    }

}