package cn.enaium.epsilon

import cn.enaium.epsilon.mixin.IClientPlayerInteractionManagerMixin
import cn.enaium.epsilon.mixin.IGameRendererMixin
import cn.enaium.epsilon.mixin.IMinecraftClientMixin
import net.minecraft.client.MinecraftClient

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
object IMC {
    val mc
        get() = MinecraftClient.getInstance() as IMinecraftClientMixin

    val gameRenderer
        get() = Epsilon.MC.gameRenderer as IGameRendererMixin

    val interactionManager
        get() = Epsilon.MC.interactionManager as IClientPlayerInteractionManagerMixin
}