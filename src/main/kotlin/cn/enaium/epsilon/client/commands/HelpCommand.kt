package cn.enaium.epsilon.client.commands

import cn.enaium.cf4m.annotation.Command
import cn.enaium.cf4m.command.ICommand
import cn.enaium.cf4m.CF4M
import cn.enaium.epsilon.client.utils.EpsilonConfiguration

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Command("h", "help")
class HelpCommand : ICommand {
    override fun run(args: Array<String>): Boolean {
        CF4M.getInstance().configuration.message("Here are the list of commands:")
        for (c in CF4M.getInstance().command.commands.values) {
            for (s in c.usage()) {
                CF4M.getInstance().configuration.message("USAGE: $s")
            }
        }
        return true
    }

    override fun usage(): String {
        return "help"
    }

}