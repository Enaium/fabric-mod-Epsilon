package cn.enaium.epsilon.func

import cn.enaium.epsilon.Epsilon
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
        try {
            for (info in ClassPath.from(Thread.currentThread().contextClassLoader).topLevelClasses) {
                if (info.name.startsWith(FuncManager::class.java.`package`.name)) {
                    val clazz = Class.forName(info.name)
                    if (clazz.superclass == Func::class.java) {
                        functions.add(clazz.newInstance() as Func)
                    }
                }
            }
        } catch (throwable: Throwable) {
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