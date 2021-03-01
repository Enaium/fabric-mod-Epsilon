package cn.enaium.epsilon.client.bean

import cn.enaium.epsilon.client.cf4m

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class SettingBean(val funcBean: FuncBean, val setting: Any) {
    val name get() = cf4m.setting.getName(funcBean.func, setting)
    val description get() = cf4m.setting.getDescription(funcBean.func, setting)
}