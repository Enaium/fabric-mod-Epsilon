package cn.enaium.epsilon.client

import cn.enaium.cf4m.CF4M
import net.minecraft.client.MinecraftClient


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */

val MC: MinecraftClient = MinecraftClient.getInstance()

object Epsilon {

    val NAME = "Epsilon"
    val VERSION = "1.0.4"
    val GAME = "21w07a"
    val AUTHOR = "Enaium"


    fun run() {
        CF4M.INSTANCE.run(this, MinecraftClient.getInstance().runDirectory.toString() + "/" + NAME)
    }

    fun stop() {
        CF4M.INSTANCE.stop()
    }

}