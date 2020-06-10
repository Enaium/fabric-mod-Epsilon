package cn.enaium.epsilon.screen.clickgui

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.ui.UI
import cn.enaium.epsilon.ui.elements.Button
import cn.enaium.epsilon.ui.elements.ScrollPanel
import cn.enaium.epsilon.utils.Render2DUtils
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class FuncListScreen(val category: Category) : UI() {

    override fun initUI() {
        super.initUI()
        val scrollPanel = ScrollPanel(Render2DUtils.scaledWidth / 2 - 50, 50, 100, 120)
        var y = 0
        for (func in Epsilon.funcManager.getFuncForCategory(category)) {
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