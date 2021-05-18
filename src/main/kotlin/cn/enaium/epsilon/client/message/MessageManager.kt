package cn.enaium.epsilon.client.message

import net.minecraft.client.util.math.MatrixStack
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.ArrayList

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class MessageManager {
    private val messages: LinkedList<Message> = LinkedList()

    fun post(add: Message) {
        for (message in messages) {
            if (message.message == add.message && message.type == add.type) {
                messages.remove(message)
                messages.add(0, message)
                return
            }
        }
        messages.add(0, add)
    }

    fun render(matrixStack: MatrixStack) {
        for ((index, message) in messages.withIndex()) {
            message.render(matrixStack, index)
        }
    }

    fun remove() {
        messages.removeIf { !it.isShow() }
    }
}