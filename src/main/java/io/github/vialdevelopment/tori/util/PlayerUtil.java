package io.github.vialdevelopment.tori.util;

import io.github.vialdevelopment.tori.client.events.MoveEvent;
import net.minecraft.client.MinecraftClient;

public class PlayerUtil {

    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void setXMovement(MoveEvent event, double speed) {
        double forward = mc.player.input.movementForward;
        double strafe = mc.player.input.movementSideways;
        float yaw = mc.player.getYaw(mc.getTickDelta());
        if (forward == 0.0 && strafe == 0.0) {
            event.x = 0.0;
            event.z = 0.0;
            mc.player.setVelocity(0, mc.player.getVelocity().y, 0);
        } else {
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
            final double x = forward * speed * -Math.sin(Math.toRadians(yaw)) + strafe * speed * Math.cos(Math.toRadians(yaw));

            final double z = forward * speed * Math.cos(Math.toRadians(yaw)) - strafe * speed * -Math.sin(Math.toRadians(yaw));

            event.x = x;
            event.z = z;
            mc.player.setVelocity(x, mc.player.getVelocity().y, z);
        }
    }
}
