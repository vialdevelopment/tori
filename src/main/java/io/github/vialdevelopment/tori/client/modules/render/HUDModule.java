package io.github.vialdevelopment.tori.client.modules.render;

import io.github.vialdevelopment.attendance.attender.Attend;
import io.github.vialdevelopment.attendance.attender.Attender;
import io.github.vialdevelopment.tori.api.runnable.impl.Category;
import io.github.vialdevelopment.tori.api.runnable.impl.Module;
import io.github.vialdevelopment.tori.api.setting.Setting;
import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.events.Render2DEvent;
import io.github.vialdevelopment.tori.util.FontUtil;
import io.github.vialdevelopment.tori.util.Logger;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class HUDModule extends Module {

    private final Setting<Boolean> watermark = new Setting<>("Watermark", true);

    private final Setting<Float> textScale = new Setting<>("TextScale", 1f);

    public HUDModule() {
        super("HUD", "A heads up display", Category.RENDER);
    }

    private final Color color = Color.LIGHT_GRAY;

    private float increment = 0;


    @Attend
    private final Attender<Render2DEvent> render2DEventAttender = new Attender<>(Render2DEvent.class, event -> {
        if (mc.world == null || mc.player == null) return;
        this.drawArrayList(event.matrixStack);
        this.drawCoordinates(event.matrixStack);
    });


    private void drawArrayList(MatrixStack matrixStack) {
        final float xOffset = 2;

        float currentHeight = 2;

        if (this.watermark.getValue()) FontUtil.drawShadowedString(matrixStack, Tori.MOD_NAME, xOffset, currentHeight, this.color.getRGB(), this.textScale.getValue());

        currentHeight += this.getIncrement();

        if (Tori.INSTANCE.moduleManager.getModules().isEmpty()) Logger.log("WHAT");

        for (Module module : Tori.INSTANCE.moduleManager.getModules()) {
            if (!module.getState()) continue;
            FontUtil.drawShadowedString(matrixStack, module.getName() + module.getModuleInfo(), xOffset, currentHeight, Color.WHITE.getRGB(), this.textScale.getValue());
            currentHeight += this.getIncrement();
        }
    }

    private void drawCoordinates(MatrixStack matrixStack) {
        final Entity player = mc.player;
        final Window scaledResolution = mc.getWindow();

        final Vec3d normalCoords = mc.player.getPos();
        final Vec3d otherCoords =(mc.world.getDimension().isUltrawarm()) ? player.getPos().multiply(8) : mc.player.getPos().multiply(1d / 8d);

        FontUtil.drawShadowedString(matrixStack, (int) normalCoords.x + ", " + (int) normalCoords.y + ", " + (int) normalCoords.z,
                2f, scaledResolution.getScaledHeight() - ((mc.inGameHud.getChatHud().isChatFocused()) ? 24f : 12f), Color.WHITE.getRGB(), this.textScale.getValue());

        FontUtil.drawShadowedString(matrixStack, (int) otherCoords.x + ", " + (int) otherCoords.y + ", " + (int) otherCoords.z,
                2f, scaledResolution.getScaledHeight() - (((mc.inGameHud.getChatHud().isChatFocused()) ? 24f : 12f) + this.getIncrement()), Color.RED.getRGB(), this.textScale.getValue());

    }

    public float getIncrement() {
        if (this.increment == 0) {
            this.increment = FontUtil.getFontHeight(this.textScale.getValue()) + 2;
        }
        return this.increment;
    }


}
