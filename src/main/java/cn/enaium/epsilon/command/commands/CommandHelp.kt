package cn.enaium.epsilon.command.commands

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.command.Command
import cn.enaium.epsilon.utils.ChatUtils

class CommandHelp : Command {
    override fun run(args: Array<String>): Boolean {
        ChatUtils.message("Here are the list of commands:")
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