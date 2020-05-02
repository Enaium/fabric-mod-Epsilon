package cn.enaium.epsilon.command.commands

import cn.enaium.epsilon.Epsilon
import cn.enaium.epsilon.command.Command
import cn.enaium.epsilon.utils.ChatUtils
import cn.enaium.epsilon.utils.I18nUtils

class CommandToggle : Command {
    override fun run(args: Array<String>): Boolean {

        if (args.size == 1 || args.size == 2) {
            if (args.size == 1) {
                for (ms in Epsilon.moduleManager.modules) ChatUtils.message(ms.name)
                return true
            } else if (args.size == 2) {
                val module = Epsilon.moduleManager.getModule(args[1])
                if (module == null) {
                    ChatUtils.error(I18nUtils.getKey("command.toggle").replace("{{args[1]}}", args[1]))
                    return true
                }
                module.enable()
                return true
            }
        }
        return false
    }

    override fun usage(): Array<String> {
        return arrayOf("-toggle [module]")
    }
}