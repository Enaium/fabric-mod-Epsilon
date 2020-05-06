package cn.enaium.epsilon.command.commands

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.command.Command
import cn.enaium.epsilon.command.CommandAT
import cn.enaium.epsilon.utils.ChatUtils

@CommandAT(["enable", "e"])
class EnableCommand : Command {
    override fun run(args: Array<String>): Boolean {

        if (args.size == 1 || args.size == 2) {
            if (args.size == 1) {
                for (ms in Epsilon.moduleManager.modules) ChatUtils.message(ms.name)
                return true
            } else if (args.size == 2) {
                val module = Epsilon.moduleManager.getModule(args[1])
                if (module == null) {
                    ChatUtils.error("The module with the name " + args[1] + " does not exist.")
                    return true
                }
                module.enable()
                return true
            }
        }
        return false
    }

    override fun usage(): Array<String> {
        return arrayOf("enable [module]")
    }
}