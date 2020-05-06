package cn.enaium.epsilon.command

import cn.enaium.epsilon.utils.ChatUtils
import com.google.common.reflect.ClassPath
import java.util.*

class CommandManager {
    var commands: HashMap<Array<String>, Command> = HashMap()
    private val prefix: String = "`"

    init {

        for (info in ClassPath.from(Thread.currentThread().contextClassLoader).topLevelClasses) {
            if (info.name.startsWith("cn.enaium.epsilon.command.commands")) {
                val clazz: Class<*> = Class.forName(info.load().name)
                if (clazz.isAnnotationPresent(CommandAT::class.java)) {
                    commands[clazz.getAnnotation(CommandAT::class.java).value] = clazz.newInstance() as Command
                }
            }
        }
    }

    fun processCommand(rawMessage: String): Boolean {
        if (!rawMessage.startsWith(prefix)) {
            return false
        }
        val safe = rawMessage.split(prefix.toRegex()).toTypedArray().size > 1
        if (safe) {
            val beheaded = rawMessage.split(prefix.toRegex()).toTypedArray()[1]
            val args = beheaded.split(" ".toRegex()).toTypedArray()
            val command = getCommand(args[0])
            if (command != null) {
                if (!command.run(args)) {
                    for (s in command.usage()) {
                        ChatUtils.error("USAGE:`$s")
                    }
                }
            } else {
                ChatUtils.error("Try `help.")
            }
        } else {
            ChatUtils.error("Try `help.")
        }
        return true
    }

    private fun getCommand(name: String): Command? {
        for ((key1, value) in commands) {
            for (s in key1) {
                if (s.equals(name, ignoreCase = true)) {
                    return value
                }
            }
        }
        return null
    }
}