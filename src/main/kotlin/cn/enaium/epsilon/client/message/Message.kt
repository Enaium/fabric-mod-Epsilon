package cn.enaium.epsilon.client.message

import cn.enaium.epsilon.client.utils.FontUtils
import cn.enaium.epsilon.client.utils.Render2DUtils
import net.minecraft.client.util.math.MatrixStack
import java.awt.Color

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class Message(val message: String, val type: Type) {

    private var end = 3500
    private var start = System.currentTimeMillis()

    private val time get() = (System.currentTimeMillis() - start).toInt()

    fun isShow(): Boolean {
        return time <= end
    }

    fun render(matrixStack: MatrixStack, index: Int) {
        val width = 120
        val height = 30

        val x = Render2DUtils.scaledWidth - width
        val y = Render2DUtils.scaledHeight - (height + 10) * (index + 1)

        val color = when (this.type) {
            Type.SUCCESS -> {
                Color(0, 255, 0).rgb
            }
            Type.ERROR -> {
                Color(255, 0, 0).rgb
            }
            Type.WARNING -> {
                Color(255, 255, 0).rgb
            }
            else -> {
                Color(0, 0, 255).rgb
            }
        }

        Render2DUtils.drawRectWH(matrixStack, x, y, width, height, Color(0, 0, 0).rgb)
        Render2DUtils.drawRectWH(matrixStack, x, y, 10, height, color)
        drawProgressBarWH(matrixStack, x, y + height - 5, width, 5, end, time, Color(0, 255, 255).rgb)
        FontUtils.drawString(matrixStack, this.message, x, y, 0xFFFFFF)

    }

    private fun drawProgressBarWH(
        matrixStack: MatrixStack,
        x: Int,
        y: Int,
        width: Int,
        height: Int,
        value: Int,
        current: Int,
        color: Int
    ) {
        val each: Float = width.toFloat() / value.toFloat()
        Render2DUtils.drawRectWH(matrixStack, x, y, (each * current).toInt(), height, color)
    }


    enum class Type {
        SUCCESS, ERROR, WARNING, NONE
    }
}