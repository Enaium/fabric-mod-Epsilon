package cn.enaium.epsilon.module.modules.render

import cn.enaium.epsilon.module.Category
import cn.enaium.epsilon.module.Module
import cn.enaium.epsilon.module.ModuleAT
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
@ModuleAT
class ClickGUIModule : Module("ClickGUI", GLFW.GLFW_KEY_RIGHT_SHIFT, Category.RENDER) {
    override fun onEnable() {

        enable()
        super.onEnable()
    }
}