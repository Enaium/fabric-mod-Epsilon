package cn.enaium.epsilon.module

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.event.EventTarget
import cn.enaium.epsilon.event.events.EventKeyboard
import cn.enaium.epsilon.module.modules.combat.AuraModule
import cn.enaium.epsilon.module.modules.movement.SprintModule
import cn.enaium.epsilon.module.modules.render.BrightModule
import cn.enaium.epsilon.module.modules.render.ClickGUIModule
import cn.enaium.epsilon.module.modules.render.GlowModule
import cn.enaium.epsilon.module.modules.render.HUDModule
import com.google.common.reflect.ClassPath
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
    }

    fun load() {
        for (info in ClassPath.from(Thread.currentThread().contextClassLoader).topLevelClasses) {
            if (info.name.startsWith("cn.enaium.epsilon.module.modules")) {
                val clazz: Class<*> = info.load()
                if (Class.forName(clazz.name).isAnnotationPresent(ModuleAT::class.java)) {
                    modules.add(Class.forName(clazz.name).newInstance() as Module)
                }
            }
        }
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