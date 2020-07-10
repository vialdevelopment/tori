package io.github.vialdevelopment.tori.client.modules.player;

import io.github.vialdevelopment.attendance.attender.Attend;
import io.github.vialdevelopment.attendance.attender.Attender;
import io.github.vialdevelopment.tori.api.runnable.impl.Category;
import io.github.vialdevelopment.tori.api.runnable.impl.Module;
import io.github.vialdevelopment.tori.api.setting.Setting;
import io.github.vialdevelopment.tori.client.events.UpdateCameraEvent;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.util.math.Vec3d;

public class FreeCamModule extends Module {

    private final Setting<Double> speed = new Setting<>("Speed", 1d);

    public FreeCamModule() {
        super("FreeCam", "Allows you to move around freely from your body!", Category.PLAYER);
    }

    private Vec3d pos;

    private float yaw, pitch;

    private Input input = new KeyboardInput(mc.options);

    @Override
    public void onEnable() {
        super.onEnable();
        this.pos = mc.getEntityRenderManager().camera.getPos();
    }

    @Attend
    public Attender<UpdateCameraEvent> updateCameraEvent = new Attender<>(UpdateCameraEvent.class, event -> {
        if (mc.player == null) return;
        this.setMoveSpeed(this.input, this.speed.getValue());
        event.pos = this.pos;
        event.setCanceled(true);
    });

    private void setMoveSpeed(Input input, double speed) {
        double forward = input.movementForward;
        double strafe = input.movementSideways;

        final Vec3d pos = this.pos;

        if (input.jumping) pos.add(0, this.speed.getValue() / 3, 0);
        if (input.sneaking) pos.add(0, -this.speed.getValue() / 3, 0);
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
            pos.add(forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw)), 0, forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw)));
        }
        this.pos = pos;
    }
}
