package cn.enaium.epsilon.imixin

import net.minecraft.client.util.Session
import net.minecraft.client.util.Window

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
interface IMinecraftClient {
    fun window(): Window
    fun setSession(session: Session)
}