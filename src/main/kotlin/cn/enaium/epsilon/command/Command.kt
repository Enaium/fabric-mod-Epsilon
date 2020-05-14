package cn.enaium.epsilon.command

interface Command {
    fun run(args: Array<String>): Boolean
    fun usage(): Array<String>
}