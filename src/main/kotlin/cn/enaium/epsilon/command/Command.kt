package cn.enaium.epsilon.command

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
interface Command {
    fun run(args: Array<String>): Boolean
    fun usage(): Array<String>
}