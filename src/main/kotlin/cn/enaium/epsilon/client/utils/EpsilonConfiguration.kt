package cn.enaium.epsilon.client.utils

import cn.enaium.cf4m.annotation.Configuration
import cn.enaium.cf4m.configuration.IConfiguration
import cn.enaium.epsilon.client.Epsilon
import cn.enaium.epsilon.client.MC
import net.minecraft.text.LiteralText
import net.minecraft.util.Formatting

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Configuration
class EpsilonConfiguration : IConfiguration {
    /**
     * Message
     * @param string Message
     */
    override fun message(string: String) {
        MC.inGameHud.chatHud.addMessage(LiteralText("[" + Formatting.GRAY + Epsilon.NAME + Formatting.WHITE + "]" + string))
    }
}