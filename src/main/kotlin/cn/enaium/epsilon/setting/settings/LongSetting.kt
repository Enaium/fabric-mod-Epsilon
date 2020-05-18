package cn.enaium.epsilon.setting.settings

import cn.enaium.epsilon.setting.Setting
import cn.enaium.epsilon.func.Func

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class LongSetting(func: Func, name: String, var current: Long, var min: Long, var max: Long) : Setting(func, name)