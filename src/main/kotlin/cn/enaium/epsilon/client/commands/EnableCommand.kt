package cn.enaium.epsilon.client.commands

import cn.enaium.cf4m.annotation.Command
import cn.enaium.cf4m.command.ICommand
import cn.enaium.epsilon.client.cf4m

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Command("e", "enable")
class EnableCommand : ICommand {
    override fun run(args: Array<String>): Boolean {

        if (args.size == 1 || args.size == 2) {
            if (args.size == 1) {
                for (ms in cf4m.module.modules)
                    cf4m.configuration.message(cf4m.module.getName(ms))
                return true
            } else if (args.size == 2) {
                val func = cf4m.module.getModule(args[1])
                if (func == null) {
                    error("The func with the name " + args[1] + " does not exist.")
                    return true
                }
                cf4m.module.enable(func)
                return true
            }
        }
        return false
    }

    override fun usage(): String {
        return "<func>"
    }
}