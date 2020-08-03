package io.github.vialdevelopment.tori.client.modules.movement;

import io.github.vialdevelopment.attendance.attender.Attend;
import io.github.vialdevelopment.attendance.attender.Attender;
import io.github.vialdevelopment.tori.api.runnable.impl.module.Category;
import io.github.vialdevelopment.tori.api.runnable.impl.module.Module;
import io.github.vialdevelopment.tori.api.setting.Setting;
import io.github.vialdevelopment.tori.client.events.MoveEvent;
import io.github.vialdevelopment.tori.util.PlayerUtil;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class ElytraFlyModule extends Module {

    private final Setting<Double> speed = new Setting<>("Speed", 1d);
    private final Setting<Double> descendSpeed = new Setting<>("Descent", -0.000101d);
    private final Setting<Boolean> up = new Setting<>("Up", true);
    private final Setting<Boolean> infiniteDurability = new Setting<>("InfiniteDurability", false);

    public ElytraFlyModule() {
        super("ElytraFly", "Vroom", Category.MOVEMENT);
    }

    @Attend
    private final Attender<MoveEvent> moveEvent = new Attender<>(MoveEvent.class, event -> {

        if (mc.player == null) return;

        event.setCanceled(true);

        if (mc.player.isFallFlying()) {

            if (this.infiniteDurability.getValue()) {
                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
            }

            if (!this.up.getValue()) {
                if (!mc.options.keyJump.isPressed()) {
                    if (mc.options.keySneak.isPressed()) {
                        mc.player.setVelocity(mc.player.getVelocity().x, -(this.speed.getValue() / 2), mc.player.getVelocity().z);
                        event.y = -(this.speed.getValue() / 2);
                    } else if (event.y != this.descendSpeed.getValue()) {
                        event.y = (this.descendSpeed.getValue());
                        mc.player.setVelocity(mc.player.getVelocity().x, this.descendSpeed.getValue(), mc.player.getVelocity().z);
                    }
                }
            } else {
                if (!mc.options.keyJump.isPressed() && !mc.options.keySneak.isPressed()) {
                    if (event.y != this.descendSpeed.getValue()) {
                        event.y = (this.descendSpeed.getValue());
                    }
                    mc.player.setVelocity(mc.player.getVelocity().x, 0, mc.player.getVelocity().z);
                } else {
                    mc.player.setVelocity(mc.player.getVelocity().x, 0, mc.player.getVelocity().z);
                    double motion = 0;

                    if (mc.options.keyJump.isPressed()) {

                        motion += this.speed.getValue() / 2;
                    }
                    if (mc.options.keySneak.isPressed()) {
                        motion -= (this.speed.getValue() / 2);
                    }
                    mc.player.setVelocity(mc.player.getVelocity().x, motion, mc.player.getVelocity().z);
                }
            }

            PlayerUtil.setXMovement(event, this.speed.getValue());


            if (this.infiniteDurability.getValue()) {
                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
                mc.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.START_FALL_FLYING));
            }
        }
    });
}
