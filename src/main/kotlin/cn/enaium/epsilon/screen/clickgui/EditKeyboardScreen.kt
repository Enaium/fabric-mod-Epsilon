package cn.enaium.epsilon.screen.clickgui

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.ui.UI
import cn.enaium.epsilon.ui.elements.Button
import cn.enaium.epsilon.ui.elements.Label
import net.minecraft.client.util.math.MatrixStack
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class EditKeyboardScreen(val func: Func) : UI() {
    override fun initUI() {
        super.initUI()
        addElement(Label(width / 2 - 50, height / 2 - 10, "Key:${func.keyCode}"))
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            onClose()
        } else {
            func.keyCode = keyCode
            onClose()
        }
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun isPauseScreen(): Boolean {
        return false
    }

    override fun shouldCloseOnEsc(): Boolean {
        return false
    }
}