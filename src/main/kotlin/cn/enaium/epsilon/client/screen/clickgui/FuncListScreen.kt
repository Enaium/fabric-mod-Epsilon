package cn.enaium.epsilon.client.screen.clickgui

import cn.enaium.cf4m.CF4M
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.client.ui.UI
import cn.enaium.epsilon.client.ui.elements.Button
import cn.enaium.epsilon.client.ui.elements.ScrollPanel
import cn.enaium.epsilon.client.utils.Render2DUtils
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class FuncListScreen(val category: Category) : UI() {

    override fun initUI() {
        super.initUI()
        val scrollPanel = ScrollPanel(Render2DUtils.scaledWidth / 2 - 50, 50, 100, 120)
        var y = 0
        for (func in CF4M.module.getAllByCategory(category)) {
            scrollPanel.addElement(object : Button(
                0,
                y,
                func.name
            ) {
                override fun onLeftClicked() {
                    func.enable()
                    super.onLeftClicked()
                }

                override fun onRightClicked() {
                    MC.openScreen(SettingListScreen(func))
                    super.onRightClicked()
                }

                override fun onMiddleClicked() {
                    MC.openScreen(EditKeyboardScreen(func))
                    super.onMiddleClicked()
                }
            })
            y += 30
        }
        addElement(scrollPanel)
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE)
            MC.openScreen(CategoryListScreen())
        return super.keyPressed(keyCode, scanCode, modifiers)
    }

    override fun isPauseScreen(): Boolean {
        return false
    }

    override fun shouldCloseOnEsc(): Boolean {
        return false
    }
}