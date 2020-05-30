package cn.enaium.epsilon.screen.clickgui.panel.element

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.screen.clickgui.screen.setting.EditValueSettingScreen
import cn.enaium.epsilon.setting.Setting
import cn.enaium.epsilon.setting.settings.EnableSetting
import cn.enaium.epsilon.utils.ColorUtils
import cn.enaium.epsilon.utils.FontUtils
import cn.enaium.epsilon.utils.Render2DUtils
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class ValueElementPanel(setting: Setting) : ElementPanel(setting) {

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
        super.render(matrices, mouseX, mouseY, delta, slotX, slotY)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {
        if (hovering && button == 0) {
            MC.openScreen(EditValueSettingScreen(setting))
        }
        super.mouseClicked(mouseX, mouseY, button)
    }

}