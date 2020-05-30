package cn.enaium.epsilon.screen.clickgui.panel

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.screen.clickgui.screen.setting.EditSettingScreen
import cn.enaium.epsilon.utils.ColorUtils
import cn.enaium.epsilon.utils.FontUtils
import cn.enaium.epsilon.utils.Render2DUtils
import net.minecraft.client.util.math.MatrixStack

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class FuncPanel(var func: Func, var width: Double, var height: Double) {

    private var hovering = false

    fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float, x: Double, y: Double) {
        hovering = Render2DUtils.isHovered(mouseX.toDouble(), mouseY.toDouble(), x, y, width, height)
        Render2DUtils.drawRect(matrices, x, y, x + width, y + height, ColorUtils.BG)
        var color = ColorUtils.ASPHALT
        if (func.enable) {
            color = ColorUtils.TURKIC
        }
        if (hovering) {
            color = ColorUtils.SELECT
        }
        Render2DUtils.drawRect(matrices, x + 2, y + 2, x + width - 2, y + height - 2, color)
        FontUtils.drawHVCenteredString(
            matrices,
            func.name,
            (x + (x + width)) / 2,
            (y + (y + height)) / 2,
            ColorUtils.WHITE
        )
    }

    fun mouseClicked(mouseX: Double, mouseY: Double, button: Int) {
        if (hovering) {
            if (button == 0) {
                func.enable()
            } else if (button == 1) {
                if (Epsilon.settingManager.getSettingsForFunc(func) != null) {
                    MC.openScreen(
                        EditSettingScreen(
                            func
                        )
                    )
                }
            }
        }
    }

}