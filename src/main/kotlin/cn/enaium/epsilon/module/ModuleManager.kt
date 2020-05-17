package cn.enaium.epsilon.module

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.event.EventAT
import cn.enaium.epsilon.event.events.KeyboardEvent
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
            if (info.name.startsWith(Module::class.java.`package`.name)) {
                val clazz: Class<*> = Class.forName(info.load().name)
                if (clazz.isAnnotationPresent(ModuleAT::class.java)) {
                    modules.add(clazz.newInstance() as Module)
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

    @EventAT
    fun onKey(keyBoardEvent: KeyboardEvent) {

        if (Epsilon.MC.currentScreen != null)
            return

        if (keyBoardEvent.action != GLFW.GLFW_PRESS)
            return

        for (mod in modules) {
            if (mod.keyCode == keyBoardEvent.keyCode) mod.enable()
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