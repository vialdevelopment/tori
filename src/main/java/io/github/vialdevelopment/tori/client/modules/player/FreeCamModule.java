package io.github.vialdevelopment.tori.client.modules.player;

import io.github.vialdevelopment.attendance.attender.Attend;
import io.github.vialdevelopment.attendance.attender.Attender;
import io.github.vialdevelopment.tori.api.event.EventStage;
import io.github.vialdevelopment.tori.api.runnable.module.Category;
import io.github.vialdevelopment.tori.api.runnable.module.Module;
import io.github.vialdevelopment.tori.client.events.InputEvent;
import io.github.vialdevelopment.tori.client.events.UpdateCameraEvent;
import io.github.vialdevelopment.tori.client.settings.number.DoubleSetting;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;

public class FreeCamModule extends Module {

    private final DoubleSetting speed = new DoubleSetting("Speed", 1d);

    public FreeCamModule() {
        super("FreeCam", "Allows you to move around freely from your body!", Category.PLAYER);
    }

    private double x, y, z;

    private float yaw, pitch;

    private Input input = null;

    @Override
    public void onEnable() {
        super.onEnable();
        if (mc.getEntityRenderDispatcher() == null) return;
        this.x = mc.getEntityRenderDispatcher().camera.getPos().getX();

        this.y = mc.getEntityRenderDispatcher().camera.getPos().getY();

        this.z = mc.getEntityRenderDispatcher().camera.getPos().getZ();
    }

    @Attend
    private final Attender<InputEvent> inputEventAttender = new Attender<>(InputEvent.class, event -> {
        if (mc.player == null || mc.world == null) return;
            if (event.getStage() == EventStage.EARLY) {
            if (this.input == null) {
                this.input = new KeyboardInput(mc.options);
            }
            if (!mc.options.keyPlayerList.isPressed()) {
                this.input.tick(mc.player.shouldSlowDown());
                event.setCanceled(true);
            }
        }
    });

    @Attend
    public Attender<UpdateCameraEvent> updateCameraEvent = new Attender<>(UpdateCameraEvent.class, event -> {
        if (mc.player == null || mc.world == null || this.input == null) return;
        this.setMoveSpeed(this.input, this.speed.getDoubleValue());
        event.x = this.x;
        event.y = this.y;
        event.z = this.z;
        event.setCanceled(true);
    });

    private void setMoveSpeed(Input input, double speed) {
        double forward = input.movementForward;
        double strafe = input.movementSideways;

        double x  = 0;

        double y = 0;

        double z = 0;

        if (input.jumping) y += this.speed.getDoubleValue() / 3;
        if (input.sneaking) y -= this.speed.getDoubleValue() / 3;
        float yaw = mc.player.yaw;
        if (forward != 0.0 || strafe != 0.0) {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }

            x += forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw));

            y += y;

            z += forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw));
        }

        this.x += x;

        this.y += y;

        this.z += z;
    }
}
