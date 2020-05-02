package cn.enaium.epsilon.utils

import cn.enaium.epsilon.Epsilon
import net.minecraft.client.gui.hud.ChatHud
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.util.Formatting

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
object ChatUtils {

    private val success: String = "[" + Formatting.GREEN + Epsilon.NAME + Formatting.WHITE + "] "
    private val warning: String = "[" + Formatting.YELLOW + Epsilon.NAME + Formatting.WHITE + "] "
    private val message: String = "[" + Formatting.GRAY + Epsilon.NAME + Formatting.WHITE + "] "
    private val error: String = "[" + Formatting.RED + Epsilon.NAME + Formatting.WHITE + "] "

    /**
     * Success Message
     * @param string Message
     */
    fun success(string: String) {
        Epsilon.MC.inGameHud.chatHud.addMessage(LiteralText(success + message))
    }

    /**
     * Warning Message
     * @param string Message
     */
    fun warning(string: String) {
        Epsilon.MC.inGameHud.chatHud.addMessage(LiteralText(warning + message))
    }

    /**
     * Message
     * @param string Message
     */
    fun message(string: String) {
        Epsilon.MC.inGameHud.chatHud.addMessage(LiteralText(message + message))
    }

    /**
     * Error Message
     * @param string Message
     */
    fun error(string: String) {
        Epsilon.MC.inGameHud.chatHud.addMessage(LiteralText(error + message))
    }
}