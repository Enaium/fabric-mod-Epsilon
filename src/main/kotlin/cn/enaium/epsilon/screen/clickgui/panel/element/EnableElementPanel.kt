package cn.enaium.epsilon.screen.clickgui.panel.element

import cn.enaium.epsilon.setting.settings.EnableSetting
import cn.enaium.epsilon.utils.ColorUtils
import cn.enaium.epsilon.utils.Render2DUtils
import net.minecraft.client.util.math.MatrixStack

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class EnableElementPanel(private var enableSetting: EnableSetting) : ElementPanel(enableSetting) {

    private var hovering = false

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float, slotX: Double, slotY: Double) {
        hovering = Render2DUtils.isHovered(mouseX.toDouble(), mouseY.toDouble(), slotX + space, slotY, height, height)
        var color = ColorUtils.DISABLE
        if (enableSetting.enable) {
            color = ColorUtils.TURKIC
        }
        if (hovering) {
            color = ColorUtils.SELECT
        }
        Render2DUtils.drawRectWH(matrices, slotX + space, slotY, height, height, color)
        super.render(matrices, mouseX, mouseY, delta, slotX, slotY)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {
        if (hovering && button == 0) {
            enableSetting.enable = !enableSetting.enable
        }
        super.mouseClicked(mouseX, mouseY, button)
    }

}