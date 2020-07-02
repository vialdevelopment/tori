package io.github.vialdevelopment.tori.util;

import io.github.vialdevelopment.tori.client.Tori;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

/**
 * @author cats
 * @since 20 Mar 2020
 */
public class Logger {

    /**
     * the thing used to divide the prefix from the message
     */
    public static final String divider = " = ";

    /**
     * the prefix for logged messages
     */
    private static final String prefix = Formatting.DARK_BLUE + Tori.INSTANCE.MOD_NAME + Formatting.WHITE + divider;


    /**
     * Logs a message
     * @param message the message to log
     */
    public static void log(String message) {
        final String toSend = prefix + message;

        if (MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.sendMessage(new TranslatableText(toSend), false);
        } else {
            System.out.println(toSend);
        }
    }

    /**
     * logs a message without the fancy prefix
     * @param message the message to log
     */
    public static void logRawText(String message) {
        if (MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.sendMessage(new TranslatableText(message), false);
        } else {
            System.out.println(message);
        }
    }

    /**
     * logs a message with System.out.println
     * @param message the message to log
     */
    public static void println(String message) {
        System.out.println(message);
    }
}
