package cn.enaium.epsilon.client

import cn.enaium.cf4m.CF4M
import cn.enaium.epsilon.client.cf4m
import net.minecraft.client.MinecraftClient


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */

val MC: MinecraftClient = MinecraftClient.getInstance()
val cf4m = CF4M.INSTANCE

object Epsilon {

    val NAME = "Epsilon"
    val VERSION = "1.0.4"
    val GAME = "21w07a"
    val AUTHOR = "Enaium"


    fun run() {
        cf4m.run(this, MinecraftClient.getInstance().runDirectory.toString() + "/" + NAME)
    }

    fun stop() {
        cf4m.stop()
    }

}