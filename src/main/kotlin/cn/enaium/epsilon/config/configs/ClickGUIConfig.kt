package cn.enaium.epsilon.config.configs

import cn.enaium.epsilon.config.Config
import cn.enaium.epsilon.func.Category
import cn.enaium.epsilon.screen.clickgui.CategoryPanel
import cn.enaium.epsilon.screen.clickgui.ClickGUI
import cn.enaium.epsilon.utils.FileUtils
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.serializer.SerializerFeature
import java.io.File

/**
 * Project: Epsilon
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
class ClickGUIConfig : Config("ClickGUI") {

    override fun load() {
        super.load()
        try {
            val categoryPanelObject = JSONObject.parseObject(FileUtils.read(getPath()))
            for (category in Category.values()) {
                val categoryPanelClassObject = JSONObject.parseObject(categoryPanelObject.getString(category.name))
                ClickGUI.categoryPanels.add(
                    CategoryPanel(
                        category,
                        categoryPanelClassObject.getDouble("x"),
                        categoryPanelClassObject.getDouble("y")
                    )
                )
            }
        } catch (throwable: Throwable) {
            init()
        }
    }

    fun init() {
        var y = 5.0
        for (category in Category.values()) {
            ClickGUI.categoryPanels.add(CategoryPanel(category, 5.0, y))
            y += 30
        }
    }

    override fun save() {
        super.save()
        val categoryObject = JSONObject(true)
        for (categoryPanel in ClickGUI.categoryPanels) {
            val categoryPanelClassObject = JSONObject(true)
            categoryPanelClassObject["x"] = categoryPanel.x
            categoryPanelClassObject["y"] = categoryPanel.y
            categoryObject[categoryPanel.category.name] = categoryPanelClassObject
        }
        FileUtils.write(getPath(), JSON.toJSONString(categoryObject, SerializerFeature.PrettyFormat))
    }
}