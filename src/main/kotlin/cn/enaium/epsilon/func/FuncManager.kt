package cn.enaium.epsilon.func

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
class FuncManager {
    var funcs: ArrayList<Func> = ArrayList()

    init {
        Epsilon.eventManager.register(this)
    }

    fun load() {
        for (info in ClassPath.from(Thread.currentThread().contextClassLoader).topLevelClasses) {
            if (info.name.startsWith(Func::class.java.`package`.name)) {
                val clazz: Class<*> = Class.forName(info.load().name)
                if (clazz.isAnnotationPresent(FuncAT::class.java)) {
                    funcs.add(clazz.newInstance() as Func)
                }
            }
        }
    }

    fun getModule(name: String): Func? {
        for (m in funcs) {
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

        for (mod in funcs) {
            if (mod.keyCode == keyBoardEvent.keyCode) mod.enable()
        }
    }

    fun getModulesForCategory(c: Category): ArrayList<Func> {
        val modules = ArrayList<Func>()
        for (m in this.funcs) {
            if (m.category == c) {
                modules.add(m)
            }
        }
        return modules
    }

}