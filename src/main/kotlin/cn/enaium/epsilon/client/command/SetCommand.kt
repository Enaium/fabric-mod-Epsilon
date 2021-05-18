package cn.enaium.epsilon.client.command

import cn.enaium.cf4m.CF4M
import cn.enaium.cf4m.annotation.command.Command
import cn.enaium.cf4m.annotation.command.Exec
import cn.enaium.cf4m.annotation.command.Param
import cn.enaium.cf4m.provider.ModuleProvider
import cn.enaium.cf4m.provider.SettingProvider
import cn.enaium.epsilon.client.setting.BlockListSetting
import cn.enaium.epsilon.client.setting.*
import net.minecraft.util.Formatting
import kotlin.collections.ArrayList

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Suppress("ImplicitThis")
@Command("s", "set")
class SetCommand {
    lateinit var currentFunc: ModuleProvider
    lateinit var settings: ArrayList<SettingProvider>
    lateinit var currentSetting: SettingProvider

    @Exec
    fun exec(@Param("Module") moduleName: String) {
        currentFunc = CF4M.module.getByName(moduleName)
        if (currentFunc == null) {
            error("""The func with the name "$moduleName" does not exist.""", "")
        } else {
            settings = currentFunc.setting.all
            if (settings == null) {
                error("""The func with the name "$moduleName" no setting exists.""", "")
            } else {
                message("Here are the list of settings:", "")
                for (setting in settings) {
                    message(
                        setting.name,
                        "[" + setting.javaClass.simpleName + "]" + setting.description
                    )
                }
            }
        }
    }

    @Exec
    fun exec(@Param("Module") moduleName: String, @Param("Setting") settingName: String) {
        exec(moduleName)
        val setting = currentFunc.setting.getByName(settingName)
        if (setting != null) {
            currentSetting = setting
            when (val s = setting.getSetting<Any>()) {
                is EnableSetting -> {
                    message("[${setting.name}]" + "Enable:", s.enable)
                }
                is IntegerSetting -> {
                    message("[${setting.name}]" + "Current:", s.current)
                }
                is FloatSetting -> {
                    message("[${setting.name}]" + "Current:", s.current)
                }
                is DoubleSetting -> {
                    message("[${setting.name}]" + "Current:", s.current)
                }
                is LongSetting -> {
                    message("[${setting.name}]" + "Current:", s.current)
                }
                is BlockListSetting -> {
                    message("[${setting.name}]", s.blockList.toString())
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
        when (val setting = currentSetting.getSetting<Any>()) {
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
        CF4M.configuration.command().message(string + Formatting.LIGHT_PURPLE + args)
    }

    private fun error(string: String, args: Any) {
        CF4M.configuration.command().message(error + string + Formatting.LIGHT_PURPLE + args)
    }

    private fun success(string: String, args: Any) {
        CF4M.configuration.command().message(success + string + Formatting.LIGHT_PURPLE + args)
    }
}