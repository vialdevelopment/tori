package io.github.vialdevelopment.tori.client.modules.render;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.vialdevelopment.attendance.attender.Attend;
import io.github.vialdevelopment.attendance.attender.Attender;
import io.github.vialdevelopment.tori.api.runnable.module.Category;
import io.github.vialdevelopment.tori.api.runnable.module.Module;
import io.github.vialdevelopment.tori.client.events.Render3DEvent;
import io.github.vialdevelopment.tori.util.RenderUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

import java.awt.*;

public class ESPModule extends Module {
    public ESPModule() {
        super("ESP", "Shows the locations of some things", Category.RENDER);
    }

    @Attend
    private final Attender<Render3DEvent> render3DEvent = new Attender<>(Render3DEvent.class, event -> {
        if (mc.world == null) return;
        RenderSystem.pushMatrix();
        RenderUtil.startRender(event.matrixStack);
        RenderSystem.disableTexture();
        RenderSystem.depthMask(false);
        RenderSystem.disableDepthTest();

        for (Entity entity : mc.world.getEntities()) {
            if (entity != mc.player) {
                final Color color = this.getEntityColor(entity);
                if (color != null) {

                    final Box renderBox = RenderUtil.getEntityRenderBox(entity, event.tickDelta);

                    // Draw the filled box
                    RenderUtil.drawFilledBox(renderBox, new Color(color.getRed(), color.getGreen(), color.getBlue(), 40));

                    //draw the outline
                    RenderSystem.lineWidth(1.5f);
                    RenderUtil.drawBox(renderBox, color);
                }
            }
        }

        for (BlockEntity blockEntity : mc.world.blockEntities) {
            if (blockEntity == null) continue;
            if ((blockEntity instanceof ChestBlockEntity) || (blockEntity instanceof FurnaceBlockEntity)) {

                final Box box = new Box(blockEntity.getPos());

                RenderUtil.drawBox(box, Color.RED);
            }
        }

        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderUtil.endRender();
        //event.matrixStack.pop();
        RenderSystem.popMatrix();
    });

    /**
     * Get color function
     * @param entity the input entity to determine the color returned
     * @return the color returned for given entity
     */
    private Color getEntityColor(Entity entity) {
        if (entity instanceof PlayerEntity) {
            return Color.RED;
        }
        if (entity instanceof HostileEntity) {
            return Color.ORANGE.darker();
        }

        if (entity instanceof AnimalEntity) {
            return Color.GREEN;
        }
        return null;
    }
}
