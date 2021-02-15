package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.annotation.module.Enable
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.MC
import cn.enaium.cf4m.CF4M
import cn.enaium.epsilon.screen.clickgui.CategoryListScreen
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("ClickGUI", key = GLFW.GLFW_KEY_RIGHT_SHIFT, category = Category.RENDER)
class ClickGUIFunc {
    @Enable
    fun onEnable() {
        MC.openScreen(CategoryListScreen())
        CF4M.getInstance().module.enable(this)
    }
}