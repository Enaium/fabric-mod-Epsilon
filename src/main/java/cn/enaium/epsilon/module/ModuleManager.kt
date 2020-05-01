package cn.enaium.epsilon.module

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.event.EventTarget
import cn.enaium.epsilon.event.events.EventKeyboard
import cn.enaium.epsilon.module.modules.movement.SprintModule
import cn.enaium.epsilon.module.modules.render.BrightModule
import cn.enaium.epsilon.module.modules.render.GlowModule
import cn.enaium.epsilon.module.modules.render.HUDModule
import org.lwjgl.glfw.GLFW
import java.util.*


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class ModuleManager {
    var modules: ArrayList<Module> = ArrayList()

    init {
        Epsilon.eventManager.register(this)
        modules.add(SprintModule())
        modules.add(BrightModule())
        modules.add(HUDModule())
        modules.add(GlowModule())
    }

    fun getModule(name: String): Module? {
        for (m in modules) {
            if (m.name.equals(name, ignoreCase = true)) {
                return m
            }
        }
        return null
    }

    @EventTarget
    fun onKey(eventKeyBoard: EventKeyboard) {

        if (Epsilon.MC.currentScreen != null)
            return

        if (eventKeyBoard.action != GLFW.GLFW_PRESS)
            return

        for (mod in modules) {
            if (mod.keyCode == eventKeyBoard.keyCode) mod.enable()
        }
    }

    fun getModulesForCategory(c: Category): ArrayList<Module> {
        val modules = ArrayList<Module>()
        for (m in this.modules) {
            if (m.category == c) {
                modules.add(m)
            }
        }
        return modules
    }

}