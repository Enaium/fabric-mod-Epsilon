package cn.enaium.epsilon.setting.settings

import cn.enaium.epsilon.setting.Setting
import cn.enaium.epsilon.func.Func

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class IntegerSetting(func: Func, name: String, var current: Int, var min: Int, var max: Int) : Setting(func, name)