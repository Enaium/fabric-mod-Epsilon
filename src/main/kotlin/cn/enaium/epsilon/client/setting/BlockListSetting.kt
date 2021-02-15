package cn.enaium.epsilon.client.setting

import cn.enaium.cf4m.setting.SettingBase

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class BlockListSetting(module: Any, name: String, info: String, var blockList: ArrayList<String>) :
    SettingBase(module, name, info) {
}