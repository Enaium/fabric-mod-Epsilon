package cn.enaium.epsilon.client.screen.clickgui

import cn.enaium.cf4m.CF4M
import cn.enaium.epsilon.client.ui.UI
import cn.enaium.epsilon.client.ui.elements.Label
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class EditKeyboardScreen(val func: Any) : UI() {
    override fun initUI() {
        super.initUI()
        addElement(Label(width / 2 - 50, height / 2 - 10, "Key:${CF4M.INSTANCE.module.getKey(func)}"))
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            onClose()
        } else {
            CF4M.INSTANCE.module.setKey(func, keyCode)
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