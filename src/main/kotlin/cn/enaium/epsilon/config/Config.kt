package cn.enaium.epsilon.config

import cn.enaium.epsilon.Epsilon
import java.io.File

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
open class Config(var name: String) {

    open fun load() {

    }

    open fun save() {
        File(getPath()).createNewFile()
    }

    fun getPath(): String {
        return Epsilon.DIR + "/configs/" + name + ".json"
    }
}