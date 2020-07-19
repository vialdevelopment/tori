package io.github.vialdevelopment.tori.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

/**
 * @author cats
 * @since May 23, 2020
 */
public class FontUtil {

    private static final TextRenderer fontRenderer = MinecraftClient.getInstance().textRenderer;

    /**
     * draws a shadowed string with the scaling done properly
     */
    public static void drawShadowedString(MatrixStack matrixStack, String text, float x, float y, int color, float textScale) {

        GL11.glScaled(textScale, textScale,0);
        // I am going to explain this mess, I use the scale to change the font size
        getFontRenderer().drawWithShadow(matrixStack, text,
                x / textScale,
                y / textScale,
                color);
        GL11.glScaled(1 / textScale, 1 / textScale, 0);
    }

    public static float getStringWidth(String text, float textScale) {
        return getFontRenderer().getWidth(text) * textScale;
    }

    public static float getFontHeight(float textScale) {
        return getFontRenderer().fontHeight * textScale;
    }

    public static TextRenderer getFontRenderer() {
        return fontRenderer;
    }
}
