package cn.enaium.epsilon.client.func.functions.render

import cn.enaium.cf4m.CF4M
import cn.enaium.cf4m.annotation.Setting
import cn.enaium.cf4m.annotation.module.Enable
import cn.enaium.cf4m.annotation.module.Module
import cn.enaium.cf4m.module.Category
import cn.enaium.epsilon.client.IMC
import cn.enaium.epsilon.client.MC
import cn.enaium.epsilon.client.screen.clickgui.CategoryListScreen
import cn.enaium.epsilon.client.setting.EnableSetting
import org.lwjgl.glfw.GLFW

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Module("ClickGUI", key = GLFW.GLFW_KEY_RIGHT_SHIFT, category = Category.RENDER)
class ClickGUIFunc {

    @Setting("Blur")
    val blur = EnableSetting(true)

    @Enable
    fun onEnable() {
        if (blur.enable && !IMC.gameRenderer.isShadersEnabled) {
            MC.gameRenderer.toggleShadersEnabled()
        }

        MC.openScreen(CategoryListScreen())
        CF4M.module.getByInstance(this).enable()
    }
}