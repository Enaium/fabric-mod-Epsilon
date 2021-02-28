package cn.enaium.epsilon.client.commands

import cn.enaium.cf4m.annotation.command.Command
import cn.enaium.cf4m.annotation.command.Exec
import cn.enaium.cf4m.annotation.command.Param
import cn.enaium.epsilon.client.Epsilon
import cn.enaium.epsilon.client.cf4m
import cn.enaium.epsilon.client.settings.BlockListSetting
import cn.enaium.epsilon.client.settings.*
import net.minecraft.util.Formatting
import java.util.*
import kotlin.collections.ArrayList

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Command("s", "set")
class SetCommand {
    lateinit var currentFunc: Any
    lateinit var settings: ArrayList<Any>
    lateinit var currentSetting: Any

    @Exec
    fun exec(@Param("Module") moduleName: String) {
        currentFunc = cf4m.module.getModule(moduleName)
        if (currentFunc == null) {
            error("""The func with the name "$moduleName" does not exist.""", "")
        } else {
            settings = cf4m.setting.getSettings(currentFunc)
            if (settings == null) {
                error("""The func with the name "$moduleName" no setting exists.""", "")
            } else {
                message("Here are the list of settings:", "")
                for (setting in settings) {
                    message(
                        cf4m.setting.getName(currentFunc, setting),
                        "[" + setting.javaClass.simpleName + "]" + cf4m.setting.getDescription(currentFunc, setting)
                    )
                }
            }
        }
    }

    @Exec
    fun exec(@Param("Module") moduleName: String, @Param("Setting") settingName: String) {
        exec(moduleName)
        val setting = cf4m.setting.getSetting(currentFunc, settingName)
        if (setting != null) {
            currentSetting = setting
            when (setting) {
                is EnableSetting -> {
                    message("[${cf4m.setting.getName(currentFunc, setting)}]" + "Enable:", setting.enable)
                }
                is IntegerSetting -> {
                    message("[${cf4m.setting.getName(currentFunc, setting)}]" + "Current:", setting.current)
                }
                is FloatSetting -> {
                    message("[${cf4m.setting.getName(currentFunc, setting)}]" + "Current:", setting.current)
                }
                is DoubleSetting -> {
                    message("[${cf4m.setting.getName(currentFunc, setting)}]" + "Current:", setting.current)
                }
                is LongSetting -> {
                    message("[${cf4m.setting.getName(currentFunc, setting)}]" + "Current:", setting.current)
                }
                is BlockListSetting -> {
                    message("[${cf4m.setting.getName(currentFunc, setting)}]", setting.blockList.toString())
                }
            }
        } else {
            message("The setting with the name \"$settingName\" does not exist.", "")
        }
    }

    @Exec
    fun exec(
        @Param("Module") moduleName: String,
        @Param("Setting") settingName: String,
        @Param("SettingValue") settingValue: String
    ) {
        exec(moduleName, settingName)
        when (val setting = currentSetting) {
            is EnableSetting -> {
                setting.enable = settingValue.toBoolean()
                success("Enable:", setting.enable)
            }
            is IntegerSetting -> {
                setting.current = settingValue.toInt()
                success("Current:", setting.current)
            }
            is FloatSetting -> {
                setting.current = settingValue.toFloat()
                success("Current:", setting.current)
            }
            is DoubleSetting -> {
                setting.current = settingValue.toDouble()
                success("Current:", setting.current)
            }
            is LongSetting -> {
                setting.current = settingValue.toLong()
                success("Current:", setting.current)
            }
        }
    }

    private val success: String = "[" + Formatting.GREEN + "success" + Formatting.WHITE + "] "
    private val warning: String = "[" + Formatting.YELLOW + "warning" + Formatting.WHITE + "] "
    private val error: String = "[" + Formatting.RED + "error" + Formatting.WHITE + "] "

    private fun message(string: String, args: Any) {
        cf4m.configuration.message(string + Formatting.LIGHT_PURPLE + args)
    }

    private fun error(string: String, args: Any) {
        cf4m.configuration.message(error + string + Formatting.LIGHT_PURPLE + args)
    }

    private fun success(string: String, args: Any) {
        cf4m.configuration.message(success + string + Formatting.LIGHT_PURPLE + args)
    }
}