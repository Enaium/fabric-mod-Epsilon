package cn.enaium.epsilon.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Project: SpongeYay
 * -----------------------------------------------------------
 * Copyright © 2020 | Enaium | All rights reserved.
 */
public class I18nUtils {
    public static String getKey(String key) {
        return new Gson().fromJson(FileUtils.INSTANCE.readResource(I18nUtils.class.getResourceAsStream("/assets/epsilon/i18n/zh_cn.json")), JsonObject.class).get(key).getAsString();
    }
}
