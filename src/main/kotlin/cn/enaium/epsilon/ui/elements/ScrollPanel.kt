package cn.enaium.epsilon.ui.elements

import cn.enaium.epsilon.utils.Render2DUtils
import net.minecraft.client.util.math.MatrixStack
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11
import kotlin.math.max

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
open class ScrollPanel(x: Int, y: Int, width: Int, height: Int) : Element(
    x,
    y,
    width,
    height
) {

    private val elements: ArrayList<Element> = ArrayList()
    private var shiftPress = false
    var scrollDistance = 10

    fun addElement(element: Element) {
        this.elements.add(element)
    }

    fun addElementAll(vararg element: Element) {
        this.elements.addAll(element)
    }

    open fun renderBackground() {

    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        GL11.glEnable(GL11.GL_SCISSOR_TEST)
        hovered = Render2DUtils.isHovered(mouseX, mouseY, x, y, width, height)
        Render2DUtils.scissorBox(x, y, width, height)
        renderBackground()
        for (element in elements) {
            if (element.visible)
                element.render(matrices, mouseX, mouseY, delta)
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST)
    }

    override fun mouseClicked(mouseX: Double, mouseY: Double, button: Int): Boolean {
        for (element in elements) {
            if (element.visible && element.enabled && element.hovered || element is TextField)
                element.mouseClicked(mouseX, mouseY, button)
        }
        return super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseReleased(mouseX: Double, mouseY: Double, button: Int): Boolean {
        for (element in elements) {
            if (element.visible && element.enabled && element.hovered)
                element.mouseReleased(mouseX, mouseY, button)
        }
        return super.mouseReleased(mouseX, mouseY, button)
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, amount: Double): Boolean {
        for (element in elements) {
            if (element.visible && element.enabled || (element is ScrollPanel && element.hovered))
                if (shiftPress) {
                    element.scrollOffsetX += amount.toInt() * scrollDistance
                } else {
                    element.scrollOffsetY += amount.toInt() * scrollDistance
                }
            element.mouseScrolled(mouseX, mouseY, amount)
        }
        return super.mouseScrolled(mouseX, mouseY, amount)
    }

    override fun mouseDragged(mouseX: Double, mouseY: Double, button: Int, deltaX: Double, deltaY: Double): Boolean {
        for (element in elements) {
            if (element.visible && element.enabled)
                element.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {

        if (keyCode == GLFW.GLFW_KEY_LEFT_SHIFT) {
            shiftPress = true
        }

        for (element in elements) {
            element.keyPressed(keyCode, scanCode, modifiers)
        }
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun keyReleased(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {

        if (keyCode == GLFW.GLFW_KEY_LEFT_SHIFT) {
            shiftPress = false
        }

        for (element in elements) {
            element.keyReleased(keyCode, scanCode, modifiers)
        }
        return super.keyReleased(keyCode, scanCode, modifiers)
    }

    override fun charTyped(char: Char, keyCode: Int): Boolean {
        for (element in elements) {
            element.charTyped(char, keyCode)
        }
        return super.charTyped(char, keyCode)
    }
}