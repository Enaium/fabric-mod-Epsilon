package cn.enaium.epsilon.client.utils

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class TimeUtils {
    private var lastMS = 0L
    fun convertToMS(d: Int): Int {
        return 1000 / d
    }

    val currentMS: Long
        get() = System.nanoTime() / 1000000L

    fun hasReached(milliseconds: Long): Boolean {
        return currentMS - lastMS >= milliseconds
    }

    fun hasTimeReached(delay: Long): Boolean {
        return System.currentTimeMillis() - lastMS >= delay
    }

    val delay: Long
        get() = System.currentTimeMillis() - lastMS

    fun reset() {
        lastMS = currentMS
    }

    fun setLastMS() {
        lastMS = System.currentTimeMillis()
    }

    fun setLastMS(lastMS: Long) {
        this.lastMS = lastMS
    }
}