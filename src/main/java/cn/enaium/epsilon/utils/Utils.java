package cn.enaium.epsilon.utils;

import net.minecraft.client.MinecraftClient;

import java.text.DecimalFormat;
import java.util.Random;

public class Utils {

    public static float valueFix(float value) {
        DecimalFormat decimalFormat = new DecimalFormat(".0");
        return Float.parseFloat(decimalFormat.format(value));
    }

    public static double valueFix(double value) {
        DecimalFormat decimalFormat = new DecimalFormat(".0");
        return Double.parseDouble(decimalFormat.format(value));
    }
}
