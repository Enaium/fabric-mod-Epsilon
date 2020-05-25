package cn.enaium.epsilon.func.functions.render

import cn.enaium.epsilon.Epsilon.MC
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.screen.clickgui.ClickGUI
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class ClickGUIFunc : Func("ClickGUI", GLFW.GLFW_KEY_RIGHT_SHIFT, Category.RENDER) {
    override fun onEnable() {
        MC.openScreen(ClickGUI())
        enable()
        super.onEnable()
    }
}