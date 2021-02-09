package cn.enaium.epsilon.config

import cn.enaium.epsilon.Epsilon
import java.io.File

/**
 * Project: Epsilon
 * License: GPL-3.0
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
open class Config(var name: String) {

    open fun load() {

    }

    open fun save() {

    }

    fun getPath(): String {
        return Epsilon.DIR + "/configs/" + name + ".json"
    }
}