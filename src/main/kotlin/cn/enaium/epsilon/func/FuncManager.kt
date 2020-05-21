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
    var functions: ArrayList<Func> = ArrayList()

    init {
        Epsilon.eventManager.register(this)
    }

    fun load() {
        for (info in ClassPath.from(Thread.currentThread().contextClassLoader).topLevelClasses) {
            if (info.name.startsWith(Func::class.java.`package`.name)) {
                val clazz: Class<*> = Class.forName(info.load().name)
                if (clazz.isAnnotationPresent(FuncAT::class.java)) {
                    functions.add(clazz.newInstance() as Func)
                }
            }
        }
    }

    fun getFunc(name: String): Func? {
        for (m in functions) {
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

        for (mod in functions) {
            if (mod.keyCode == keyBoardEvent.keyCode) mod.enable()
        }
    }

    fun getFuncForCategory(c: Category): ArrayList<Func> {
        val functions = ArrayList<Func>()
        for (m in this.functions) {
            if (m.category == c) {
                functions.add(m)
            }
        }
        return functions
    }

}