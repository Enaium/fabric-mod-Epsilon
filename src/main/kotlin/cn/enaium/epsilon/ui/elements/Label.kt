package cn.enaium.epsilon.ui.elements

import cn.enaium.epsilon.ui.Color
import cn.enaium.epsilon.utils.FontUtils
import cn.enaium.epsilon.utils.Render2DUtils
import com.mojang.blaze3d.platform.GlStateManager
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.util.math.MatrixStack

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class Label : Element {

    var title: String
    var background = false
    var alignment: Alignment

    constructor(x: Int, y: Int, width: Int, height: Int, title: String) : super(x, y, width, height) {
        this.title = title
        this.background = true
        this.alignment = Alignment.CENTER
    }

    constructor(x: Int, y: Int, title: String) : this(x, y, 100, 20, title)

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        if (background) {
            Render2DUtils.drawRectWH(matrices, x, y, width, height, Color.Label.background)
        }
        when (this.alignment) {
            Alignment.CENTER -> {
                FontUtils.drawHVCenteredWithShadowString(
                    matrices,
                    this.title,
                    x + width / 2,
                    y + height / 2,
                    Color.title
                )
            }
            Alignment.H_CENTER -> {
                FontUtils.drawHCenteredWithShadowString(matrices, this.title, x + width / 2, y, Color.title)
            }
            Alignment.V_CENTER -> {
                FontUtils.drawVCenteredWithShadowString(matrices, this.title, x, y + height / 2, Color.title)
            }
            Alignment.NONE -> {
                FontUtils.drawStringWithShadow(matrices, this.title, x, y, Color.title)
            }
        }
    }

    enum class Alignment {
        CENTER,
        H_CENTER,
        V_CENTER,
        NONE
    }

}