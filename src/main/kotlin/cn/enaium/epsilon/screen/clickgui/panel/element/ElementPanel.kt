package cn.enaium.epsilon.screen.clickgui.panel.element

import cn.enaium.epsilon.setting.Setting
import cn.enaium.epsilon.utils.ColorUtils
import cn.enaium.epsilon.utils.FontUtils
import net.minecraft.client.util.math.MatrixStack

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
open class ElementPanel(val setting: Setting) {

    val space = 100.0
    val height = FontUtils.fontHeight.toDouble()

    open fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float, slotX: Double, slotY: Double) {
        FontUtils.drawStringWithShadow(
            matrices,
            setting.name,
            slotX,
            slotY,
            ColorUtils.WHITE
        )
    }

    open fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {

    }
}