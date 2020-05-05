package cn.enaium.epsilon

import cn.enaium.epsilon.command.CommandManager
import cn.enaium.epsilon.config.ConfigManager
import cn.enaium.epsilon.event.EventManager
import cn.enaium.epsilon.imixin.IMinecraftClient
import cn.enaium.epsilon.module.ModuleManager
import cn.enaium.epsilon.plugin.PluginManager
import cn.enaium.epsilon.setting.SettingManager
import com.mojang.brigadier.Command
import net.minecraft.client.MinecraftClient
import java.io.File


/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
object Epsilon {
    var NAME = "Epsilon"
    var VERSION = "pre1"
    var GAME = "1.15.2"
    var AUTHOR = "Enaium"
    var DIR = MinecraftClient.getInstance().runDirectory.toString() + "/" + NAME + "/"
    var MC: MinecraftClient = MinecraftClient.getInstance()
    var IMC: IMinecraftClient = MC as IMinecraftClient

    lateinit var eventManager: EventManager
    lateinit var moduleManager: ModuleManager
    lateinit var settingManager: SettingManager
    lateinit var commandManager: CommandManager
    lateinit var pluginManager: PluginManager
    lateinit var configManager: ConfigManager

    fun run() {
        File(DIR).mkdir()
        eventManager = EventManager()
        moduleManager = ModuleManager()
        settingManager = SettingManager()
        commandManager = CommandManager()
        pluginManager = PluginManager()
        configManager = ConfigManager()
        moduleManager.load()
        settingManager.load()
        configManager.load()
    }

    fun stop() {
        configManager.save()
    }
}