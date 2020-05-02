package cn.enaium.epsilon.command.commands

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.command.Command
import cn.enaium.epsilon.utils.ChatUtils
import cn.enaium.epsilon.utils.I18nUtils

class CommandHelp : Command {
    override fun run(args: Array<String>): Boolean {
        ChatUtils.message(I18nUtils.getKey("command.help"))
        for (c in Epsilon.commandManager.commands.values) {
            for (s in c.usage()) {
                ChatUtils.message("USAGE: $s")
            }
        }
        return true
    }

    override fun usage(): Array<String> {
        return arrayOf("-help")
    }
}