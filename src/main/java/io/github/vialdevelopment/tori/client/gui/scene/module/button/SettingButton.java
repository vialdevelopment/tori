package io.github.vialdevelopment.tori.client.gui.scene.module.button;

import io.github.vialdevelopment.tori.api.setting.impl.Setting;
import io.github.vialdevelopment.tori.client.gui.api.Button;
import io.github.vialdevelopment.tori.util.FontUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.*;

public class SettingButton extends Button {
    /**
     * the module setting
     */
    public Setting setting;

    public SettingButton(Setting setting) {
        super(setting.getName(), Color.GRAY);
        this.setting = setting;
    }

    /**
     * draws the button
     */
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;

        Screen.fill(matrixStack, x, y, x + width, y + height, Color.BLUE.darker().getRGB());
        FontUtil.drawShadowedString(matrixStack, this.text,
                x + 2,
                y + 2,
                -1,
                textScale);

        FontUtil.drawShadowedString(matrixStack, this.setting.getValue().toString(),
                x + ((width / 2f)
                        - (FontUtil.getStringWidth(this.setting.getValue().toString(), textScale) / 2)),
                y + 2,
                -1,
                textScale);
    }
}
