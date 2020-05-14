package cn.enaium.epsilon.utils

import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.LiteralText

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
object FontUtils {
    var tr = MinecraftClient.getInstance().textRenderer

    /**
     * Draw Text.
     * @param matrixStack MatrixStack
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawString(matrixStack: MatrixStack, text: String, x: Int, y: Int, color: Int) {
        tr.draw(matrixStack, text, x.toFloat(), y.toFloat(), color)
    }

    /**
     * Draw Text.
     * @param matrixStack MatrixStack
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawString(matrixStack: MatrixStack, text: String, x: Float, y: Float, color: Int) {
        tr.draw(matrixStack, text, x, y, color)
    }

    /**
     * Draw Shadow Text.
     * @param matrixStack MatrixStack
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawStringWithShadow(matrixStack: MatrixStack, text: String, x: Int, y: Int, color: Int) {
        tr.drawWithShadow(matrixStack, text, x.toFloat(), y.toFloat(), color)
    }

    /**
     * Draw Shadow Text.
     * @param matrixStack MatrixStack
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawStringWithShadow(matrixStack: MatrixStack, text: String, x: Double, y: Double, color: Int) {
        tr.drawWithShadow(matrixStack, text, x.toFloat(), y.toFloat(), color)
    }

    /**
     * Draw Shadow Text.
     * @param matrixStack MatrixStack
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawStringWithShadow(matrixStack: MatrixStack, text: String, x: Float, y: Float, color: Int) {
        tr.drawWithShadow(matrixStack, text, x, y, color)
    }

    /**
     * Draw Text. Horizontal Center.
     * @param matrixStack MatrixStack
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawHCenteredString(matrixStack: MatrixStack, text: String, x: Int, y: Int, color: Int) {
        tr.draw(matrixStack, text, x - tr.getWidth(LiteralText(text)) / 2.toFloat(), y.toFloat(), color)
    }

    /**
     * Draw Text. Horizontal Center.
     * @param matrixStack MatrixStack
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawHCenteredString(matrixStack: MatrixStack, text: String, x: Double, y: Double, color: Int) {
        tr.draw(matrixStack, text, x.toFloat() - tr.getWidth(LiteralText(text)) / 2, y.toFloat(), color)
    }

    /**
     * Draw Text. Horizontal Center.
     * @param matrixStack MatrixStack
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawHCenteredString(matrixStack: MatrixStack, text: String, x: Float, y: Float, color: Int) {
        tr.draw(matrixStack, text, x, y - tr.fontHeight / 2, color)
    }

    /**
     * Draw Text. Vertical Center.
     * @param matrixStack MatrixStack
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawVCenteredString(matrixStack: MatrixStack, text: String, x: Int, y: Int, color: Int) {
        tr.draw(matrixStack, text, x.toFloat(), y - tr.fontHeight / 2.toFloat(), color)
    }

    /**
     * Draw Text. Vertical Center.
     * @param matrixStack MatrixStack
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawVCenteredString(matrixStack: MatrixStack, text: String, x: Double, y: Double, color: Int) {
        tr.draw(matrixStack, text, x.toFloat(), y.toFloat() - tr.fontHeight / 2, color)
    }

    /**
     * Draw Text. Vertical Center.
     * @param matrixStack MatrixStack
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawVCenteredString(matrixStack: MatrixStack, text: String, x: Float, y: Float, color: Int) {
        tr.draw(matrixStack, text, x - tr.getWidth(LiteralText(text)) / 2, y - tr.fontHeight / 2, color)
    }

    /**
     * Draw Text. Horizontal and Vertical  Center.
     * @param matrixStack MatrixStack
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawHVCenteredString(matrixStack: MatrixStack, text: String, x: Int, y: Int, color: Int) {
        tr.draw(matrixStack, text, x - tr.getWidth(LiteralText(text)) / 2.toFloat(), y - tr.fontHeight / 2.toFloat(), color)
    }

    /**
     * Draw Text. Horizontal and Vertical  Center.
     * @param matrixStack MatrixStack
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawHVCenteredString(matrixStack: MatrixStack, text: String, x: Double, y: Double, color: Int) {
        tr.draw(matrixStack, text, x.toFloat() - tr.getWidth(LiteralText(text)) / 2, y.toFloat() - tr.fontHeight / 2, color)
    }

    /**
     * Draw Text. Horizontal and Vertical  Center.
     * @param matrixStack MatrixStack
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawHVCenteredString(matrixStack: MatrixStack, text: String, x: Float, y: Float, color: Int) {
        tr.draw(matrixStack, text, x - tr.getWidth(LiteralText(text)) / 2, y - tr.fontHeight / 2, color)
    }

    /**
     * Text Width
     * @param text Text
     */
    fun getWidth(text: String): Int {
        return tr.getWidth(LiteralText(text))
    }

    val fontHeight: Int
        get() = tr.fontHeight
}