package cn.enaium.epsilon.command.commands

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.command.Command
import cn.enaium.epsilon.command.CommandAT
import cn.enaium.epsilon.func.Func
import cn.enaium.epsilon.setting.Setting
import cn.enaium.epsilon.setting.settings.*
import cn.enaium.epsilon.utils.ChatUtils
import net.minecraft.util.Formatting
import java.util.*

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@CommandAT(["set", "s"])
class SetCommand : Command {
    override fun run(args: Array<String>): Boolean {
        if (args.size in 2..5) {

            val func = Epsilon.funcManager.getFunc(args[1])
            val settings: ArrayList<Setting>? = getSettingsForFunc(func)

            if (func == null) {
                this.error("""The func with the name "${args[1]}" does not exist.""", "")
                return true
            }

            if (settings == null) {
                this.error("""The func with the name "${args[1]}" no setting exists.""", "")
                return true
            }

            if (args.size in 3..5) {
                if (Epsilon.settingManager.getSetting(func, args[2]) == null) {
                    this.error("""The setting with the name "${args[2]}" does not exist.""", "")
                    return true
                }
            }

            if (args.size == 2) {
                this.message("Here are the list of settings:", "")
                for (s in settings) {
                    message(s.name, "[" + s.javaClass.simpleName + "]")
                }
            } else if (args.size == 3 || args.size == 4) {
                for (s in settings) {
                    if (args.size == 3) {
                        if (s.name.equals(args[2], true)) {
                            when (s) {
                                is EnableSetting -> {
                                    message("[${s.name}]" + "Enable:", s.enable)
                                }
                                is IntegerSetting -> {
                                    message("[${s.name}]" + "Current:", s.current)
                                }
                                is FloatSetting -> {
                                    message("[${s.name}]" + "Current:", s.current)
                                }
                                is DoubleSetting -> {
                                    message("[${s.name}]" + "Current:", s.current)
                                }
                                is LongSetting -> {
                                    message("[${s.name}]" + "Current:", s.current)
                                }
                                is BlockListSetting -> {
                                    message("[${s.name}]", s.blockList.toString())
                                }
                            }
                        }
                    } else if (args.size == 4) {
                        if (s.name.equals(args[2], true)) {
                            when (s) {
                                is EnableSetting -> {
                                    s.enable = args[3].toBoolean()
                                    success("Enable:", s.enable)
                                }
                                is IntegerSetting -> {
                                    s.current = args[3].toInt()
                                    success("Current:", s.current)
                                }
                                is FloatSetting -> {
                                    s.current = args[3].toFloat()
                                    success("Current:", s.current)
                                }
                                is DoubleSetting -> {
                                    s.current = args[3].toDouble()
                                    success("Current:", s.current)
                                }
                                is LongSetting -> {
                                    s.current = args[3].toLong()
                                    success("Current:", s.current)
                                }
                            }
                        }
                    }
                }
            } else if (args.size == 5) {
                if (args[3] == "add") {
                    (Epsilon.settingManager.getSetting(
                        func,
                        args[2]
                    ) as BlockListSetting).blockList.add("minecraft:" + args[4])
                    success("Added minecraft:", args[4])
                } else if (args[3] == "remove") {
                    (Epsilon.settingManager.getSetting(
                        func,
                        args[2]
                    ) as BlockListSetting).blockList.remove("minecraft:" + args[4])
                    success("Removed minecraft:", args[4])
                }
            }
            return true
        }
        return false
    }

    override fun usage(): Array<String> {
        return arrayOf(
            "set [module]",
            "set [module] [setting]",
            "set [module] [setting] [value]",
            "set [module] [setting] add [value]",
            "set [module] [setting] remove [value]"
        )
    }

    private fun getSettingsForFunc(m: Func?): ArrayList<Setting>? {
        val settings = ArrayList<Setting>()
        for (s in Epsilon.settingManager.settings) {
            if (s.func == m) settings.add(s)
        }
        return if (settings.isEmpty()) null else settings
    }

    private fun message(string: String, args: Any) {
        ChatUtils.message(string + Formatting.LIGHT_PURPLE + args)
    }

    private fun error(string: String, args: Any) {
        ChatUtils.error(string + Formatting.LIGHT_PURPLE + args)
    }

    private fun success(string: String, args: Any) {
        ChatUtils.success(string + Formatting.LIGHT_PURPLE + args)
    }

}