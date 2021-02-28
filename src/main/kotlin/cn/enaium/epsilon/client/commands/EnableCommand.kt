package cn.enaium.epsilon.client.commands

import cn.enaium.cf4m.annotation.command.Command
import cn.enaium.cf4m.annotation.command.Exec
import cn.enaium.cf4m.annotation.command.Param
import cn.enaium.epsilon.client.cf4m

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Command("e", "enable")
class EnableCommand {

    @Exec
    fun exec() {
        for (ms in cf4m.module.modules)
            cf4m.configuration.message(cf4m.module.getName(ms))
    }

    @Exec
    fun exec(@Param("") module: String) {
        val func = cf4m.module.getModule(module)
        if (func == null) {
            error("The func with the name $module does not exist.")
            return
        }
        cf4m.module.enable(func)
    }
}