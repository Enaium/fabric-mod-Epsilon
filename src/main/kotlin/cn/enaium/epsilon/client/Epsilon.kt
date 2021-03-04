package cn.enaium.epsilon.client

import cn.enaium.cf4m.CF4M
import cn.enaium.epsilon.client.cf4m
import cn.enaium.epsilon.client.message.MessageManager
import net.minecraft.client.MinecraftClient


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */

val MC: MinecraftClient = MinecraftClient.getInstance()
val cf4m = CF4M.INSTANCE

object Epsilon {

    const val NAME = "Epsilon"
    const val VERSION = "1.0.4"
    const val GAME = "21w08a"
    const val AUTHOR = "Enaium"

    lateinit var message: MessageManager

    fun run() {
        message = MessageManager()
        cf4m.run(this)
    }
}