package cn.enaium.epsilon.client.setting

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020-2021 | Enaium | All rights reserved.
 */
class EnableSetting(var enable: Boolean)
class IntegerSetting(var current: Int, var min: Int, var max: Int)
class FloatSetting(var current: Float, var min: Float, var max: Float)
class DoubleSetting(var current: Double, var min: Double, var max: Double)
class LongSetting(var current: Long, var min: Long, var max: Long)
class ModeSetting(var current: String, val modes: List<String>)
class BlockListSetting(var blockList: ArrayList<String>)