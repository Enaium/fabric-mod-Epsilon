package cn.enaium.epsilon.client.command

import cn.enaium.cf4m.CF4M
import cn.enaium.cf4m.annotation.command.Command
import cn.enaium.cf4m.annotation.command.Exec
import cn.enaium.cf4m.annotation.command.Param

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
@Command("e", "enable")
class EnableCommand {
    @Exec
    fun exec() {
        for (ms in CF4M.module.all)
            CF4M.configuration.command().message(ms.name)
    }

    @Exec
    fun exec(@Param("Module") moduleName: String) {
        val func = CF4M.module.getByName(moduleName)
        if (func == null) {
            error("The func with the name $moduleName does not exist.")
        } else {
            func.enable()
        }
    }
}