package cn.enaium.epsilon.utils

import org.apache.commons.io.FileUtils
import java.io.File
import java.io.InputStream
import java.util.*

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */

object FileUtils {
    /**
     * Read Text File
     * @param path File Path
     * @return Text Content
     */
    fun read(path: String): String {
        return FileUtils.readFileToString(File(path),"UTF-8")
    }

    /**
     * Read Jar Resource Text File
     * @param inputStream InputStream
     * @return Text Content
     */
    fun readResource(inputStream: InputStream): String {
        val scanner = Scanner(inputStream)
        val stringBuilder = StringBuilder()
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.next() + "\n")
        }
        return stringBuilder.toString()
    }

    /**
     * Write Text File
     * @param path File Path
     * @param string Text Content
     */
    fun write(path: String, string: String) {
        FileUtils.writeStringToFile(File(path), string, "UTF-8")
    }
}