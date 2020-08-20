package io.github.vialdevelopment.tori.client.modules.movement;

import io.github.vialdevelopment.attendance.attender.Attend;
import io.github.vialdevelopment.attendance.attender.Attender;
import io.github.vialdevelopment.tori.api.runnable.module.Category;
import io.github.vialdevelopment.tori.api.runnable.module.Module;
import io.github.vialdevelopment.tori.client.events.MoveEvent;
import io.github.vialdevelopment.tori.client.settings.BooleanSetting;
import io.github.vialdevelopment.tori.client.settings.number.DoubleSetting;
import io.github.vialdevelopment.tori.util.PlayerUtil;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class ElytraFlyModule extends Module {

    private final DoubleSetting speed = new DoubleSetting("Speed", 1d);
    private final DoubleSetting descendSpeed = new DoubleSetting("Descent", -0.000101d);
    private final BooleanSetting up = new BooleanSetting("Up", true);
    private final BooleanSetting infiniteDurability = new BooleanSetting("InfiniteDurability", false);

    public ElytraFlyModule() {
        super("ElytraFly", "Vroom", Category.MOVEMENT);
    }

    @Attend
    private final Attender<MoveEvent> moveEvent = new Attender<>(MoveEvent.class, event -> {

        if (mc.player == null) return;

        event.setCanceled(true);

        if (mc.player.isFallFlying()) {

            if (this.infiniteDurability.getBooleanValue()) {
                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
            }

            if (!this.up.getBooleanValue()) {
                if (!mc.options.keyJump.isPressed()) {
                    if (mc.options.keySneak.isPressed()) {
                        mc.player.setVelocity(mc.player.getVelocity().x, -(this.speed.getDoubleValue() / 2), mc.player.getVelocity().z);
                        event.y = -(this.speed.getDoubleValue() / 2);
                    } else if (event.y != this.descendSpeed.getDoubleValue()) {
                        event.y = (this.descendSpeed.getDoubleValue());
                        mc.player.setVelocity(mc.player.getVelocity().x, this.descendSpeed.getDoubleValue(), mc.player.getVelocity().z);
                    }
                }
            } else {
                if (!mc.options.keyJump.isPressed() && !mc.options.keySneak.isPressed()) {
                    if (event.y != this.descendSpeed.getDoubleValue()) {
                        event.y = (this.descendSpeed.getDoubleValue());
                    }
                    mc.player.setVelocity(mc.player.getVelocity().x, 0, mc.player.getVelocity().z);
                } else {
                    mc.player.setVelocity(mc.player.getVelocity().x, 0, mc.player.getVelocity().z);
                    double motion = 0;

                    if (mc.options.keyJump.isPressed()) {

                        motion += this.speed.getDoubleValue() / 2;
                    }
                    if (mc.options.keySneak.isPressed()) {
                        motion -= (this.speed.getDoubleValue() / 2);
                    }
                    mc.player.setVelocity(mc.player.getVelocity().x, motion, mc.player.getVelocity().z);
                }
            }

            PlayerUtil.setXMovement(event, this.speed.getDoubleValue());


            if (this.infiniteDurability.getBooleanValue()) {
                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
            }
        }
    });
}
