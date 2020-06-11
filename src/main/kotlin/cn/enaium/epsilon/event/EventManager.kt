package cn.enaium.epsilon.event

import java.lang.reflect.Method
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.HashMap

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class EventManager {
    private var hashMap: HashMap<Class<Event>, CopyOnWriteArrayList<Data>> = HashMap()

    fun register(any: Any) {
        for (method in any.javaClass.declaredMethods) {
            if (!isMethodBad(method)) {
                val clazz: Class<Event> = method.parameterTypes[0] as Class<Event>
                val methodData = Data(any, method)
                methodData.target.isAccessible = true
                if (hashMap.containsKey(clazz)) {
                    if (!hashMap[clazz]!!.contains(methodData))
                        hashMap[clazz]!!.add(methodData)
                } else {
                    hashMap[clazz] = CopyOnWriteArrayList<Data>(Collections.singletonList(methodData))
                }
            }
        }
    }

    fun unregister(any: Any) {
        hashMap.values.forEach { values -> values.removeIf { it.source == any } }
        hashMap.entries.removeIf { it.value.isEmpty() }
    }

    private fun isMethodBad(method: Method): Boolean {
        var isEvent = false
        for (parameter in method.parameters) {
            isEvent = parameter.type.superclass == Event::class.java
        }
        return method.parameterTypes.size != 1 || !isEvent
    }

    operator fun get(clazz: Class<Event>): CopyOnWriteArrayList<Data>? {
        return hashMap[clazz]
    }

}