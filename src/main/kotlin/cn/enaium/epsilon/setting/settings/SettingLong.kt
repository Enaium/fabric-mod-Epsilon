package cn.enaium.epsilon.setting.settings

import cn.enaium.epsilon.setting.Setting
import cn.enaium.epsilon.module.Module

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class SettingLong(module: Module, name: String, var current: Long, var min: Long, var max: Long) : Setting(module, name)