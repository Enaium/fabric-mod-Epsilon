package cn.enaium.epsilon.utils

import net.minecraft.client.MinecraftClient

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
object FontUtils {
    var tr = MinecraftClient.getInstance().textRenderer

    /**
     * Draw Text.
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawString(text: String, x: Int, y: Int, color: Int) {
        tr.draw(text, x.toFloat(), y.toFloat(), color)
    }

    /**
     * Draw Text.
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawString(text: String, x: Float, y: Float, color: Int) {
        tr.draw(text, x, y, color)
    }

    /**
     * Draw Shadow Text.
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawStringWithShadow(text: String, x: Int, y: Int, color: Int) {
        tr.drawWithShadow(text, x.toFloat(), y.toFloat(), color)
    }

    /**
     * Draw Shadow Text.
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawStringWithShadow(text: String, x: Double, y: Double, color: Int) {
        tr.drawWithShadow(text, x.toFloat(), y.toFloat(), color)
    }

    /**
     * Draw Shadow Text.
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawStringWithShadow(text: String, x: Float, y: Float, color: Int) {
        tr.drawWithShadow(text, x, y, color)
    }

    /**
     * Draw Text. Horizontal Center.
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawHCenteredString(text: String, x: Int, y: Int, color: Int) {
        tr.draw(text, x - tr.getStringWidth(text) / 2.toFloat(), y.toFloat(), color)
    }

    /**
     * Draw Text. Horizontal Center.
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawHCenteredString(text: String, x: Double, y: Double, color: Int) {
        tr.draw(text, x.toFloat() - tr.getStringWidth(text) / 2, y.toFloat(), color)
    }

    /**
     * Draw Text. Horizontal Center.
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawHCenteredString(text: String, x: Float, y: Float, color: Int) {
        tr.draw(text, x, y - tr.fontHeight / 2, color)
    }

    /**
     * Draw Text. Vertical Center.
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawVCenteredString(text: String, x: Int, y: Int, color: Int) {
        tr.draw(text, x.toFloat(), y - tr.fontHeight / 2.toFloat(), color)
    }

    /**
     * Draw Text. Vertical Center.
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawVCenteredString(text: String, x: Double, y: Double, color: Int) {
        tr.draw(text, x.toFloat(), y.toFloat() - tr.fontHeight / 2, color)
    }

    /**
     * Draw Text. Vertical Center.
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawVCenteredString(text: String, x: Float, y: Float, color: Int) {
        tr.draw(text, x - tr.getStringWidth(text) / 2, y - tr.fontHeight / 2, color)
    }

    /**
     * Draw Text. Horizontal and Vertical  Center.
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawHVCenteredString(text: String, x: Int, y: Int, color: Int) {
        tr.draw(text, x - tr.getStringWidth(text) / 2.toFloat(), y - tr.fontHeight / 2.toFloat(), color)
    }

    /**
     * Draw Text. Horizontal and Vertical  Center.
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawHVCenteredString(text: String, x: Double, y: Double, color: Int) {
        tr.draw(text, x.toFloat() - tr.getStringWidth(text) / 2, y.toFloat() - tr.fontHeight / 2, color)
    }

    /**
     * Draw Text. Horizontal and Vertical  Center.
     * @param text Text
     * @param x X
     * @param y Y
     * @param color Color
     */
    fun drawHVCenteredString(text: String, x: Float, y: Float, color: Int) {
        tr.draw(text, x - tr.getStringWidth(text) / 2, y - tr.fontHeight / 2, color)
    }

    /**
     * Text Width
     * @param text Text
     */
    fun getStringWidth(text: String): Int {
        return tr.getStringWidth(text)
    }

    val fontHeight: Int
        get() = tr.fontHeight
}