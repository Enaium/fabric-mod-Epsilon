package cn.enaium.epsilon.command.commands

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.command.Command
import cn.enaium.epsilon.command.CommandAT
import cn.enaium.epsilon.utils.ChatUtils.error
import cn.enaium.epsilon.utils.ChatUtils.message

@CommandAT(["enable", "e"])
class EnableCommand : Command {
    override fun run(args: Array<String>): Boolean {

        if (args.size == 1 || args.size == 2) {
            if (args.size == 1) {
                for (ms in Epsilon.funcManager.functions) message(ms.name)
                return true
            } else if (args.size == 2) {
                val func = Epsilon.funcManager.getFunc(args[1])
                if (func == null) {
                    error("The func with the name " + args[1] + " does not exist.")
                    return true
                }
                func.enable()
                return true
            }
        }
        return false
    }

    override fun usage(): Array<String> {
        return arrayOf("enable [func]")
    }
}