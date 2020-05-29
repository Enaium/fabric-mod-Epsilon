package cn.enaium.epsilon.screen.clickgui.panel.element

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.screen.clickgui.screen.setting.EditValueSettingScreen
import cn.enaium.epsilon.setting.Setting
import cn.enaium.epsilon.setting.settings.ModeSetting
import cn.enaium.epsilon.utils.ColorUtils
import cn.enaium.epsilon.utils.FontUtils
import cn.enaium.epsilon.utils.Render2DUtils
import net.minecraft.client.util.math.MatrixStack

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class ModeElementPanel(private val modeSetting: ModeSetting) : ElementPanel(modeSetting) {

    private var hovering = false

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float, slotX: Double, slotY: Double) {
        hovering = Render2DUtils.isHovered(mouseX.toDouble(), mouseY.toDouble(), slotX + space, slotY, height, height)
        Render2DUtils.drawRectWH(
            matrices,
            slotX + space,
            slotY,
            height,
            height,
            if (hovering) ColorUtils.SELECT else ColorUtils.DISABLE
        )
        if (hovering) {
            FontUtils.drawStringWithShadow(matrices, modeSetting.current, mouseX, mouseY, ColorUtils.WHITE)
        }
        super.render(matrices, mouseX, mouseY, delta, slotX, slotY)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {
        if (hovering && button == 0) {
            try {
                modeSetting.current = modeSetting.modes[modeSetting.getCurrentIndex() + 1]
            } catch (e: Exception) {
                modeSetting.current = modeSetting.modes[0]
            }
        }
        super.mouseClicked(mouseX, mouseY, button)
    }

}