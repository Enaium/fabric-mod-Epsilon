package cn.enaium.epsilon.event

import cn.enaium.epsilon.Epsilon.eventManager
import java.lang.reflect.InvocationTargetException
import java.util.function.Consumer

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
abstract class Event(val type: Type) {

    enum class Type {
        PRE, POST
    }

    fun call() {
        val dataList = eventManager[this.javaClass] ?: return
        dataList.forEach(Consumer { data: Data ->
            try {
                data.target.invoke(data.source, this)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }
        })
    }

}