package cn.enaium.epsilon.client.commands

import cn.enaium.cf4m.annotation.Command
import cn.enaium.cf4m.command.ICommand
import cn.enaium.epsilon.client.cf4m
import cn.enaium.epsilon.client.utils.EpsilonConfiguration

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Command("h", "help")
class HelpCommand : ICommand {
    override fun run(args: Array<String>): Boolean {
        cf4m.configuration.message("Here are the list of commands:")
        for (c in cf4m.command.commands.values) {
            for (s in c.usage()) {
                cf4m.configuration.message("USAGE: $s")
            }
        }
        return true
    }

    override fun usage(): String {
        return "help"
    }

}