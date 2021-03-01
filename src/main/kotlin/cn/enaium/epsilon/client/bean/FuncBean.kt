package cn.enaium.epsilon.client.bean

import cn.enaium.epsilon.client.cf4m

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class FuncBean(val func: Any) {
    val name: String get() = cf4m.module.getName(func)
    val description get() = cf4m.module.getDescription(func)
    val category get() = cf4m.module.getCategory(func)
    val enable get() = cf4m.module.getEnable(func)
    var key = 0
        get() = cf4m.module.getKey(func)
        set(value) {
            cf4m.module.setKey(func, value)
            field = value
        }
}