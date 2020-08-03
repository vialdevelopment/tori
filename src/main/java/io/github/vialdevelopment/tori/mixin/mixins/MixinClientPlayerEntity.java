package io.github.vialdevelopment.tori.mixin.mixins;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.vialdevelopment.tori.api.event.EventStage;
import io.github.vialdevelopment.tori.client.Tori;
import io.github.vialdevelopment.tori.client.events.InputEvent;
import io.github.vialdevelopment.tori.client.events.MoveEvent;
import io.github.vialdevelopment.tori.client.events.SendMovementPacketEvent;
import io.github.vialdevelopment.tori.util.Logger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity extends AbstractClientPlayerEntity {

    private SendMovementPacketEvent sendMovementPacketEvent;

    @Shadow
    protected abstract void autoJump(float dx, float dz);

    @Shadow public Input input;
    @Shadow public abstract boolean isHoldingSneakKey();

    /**
     * keeps the compiler happy
     * -famous1622/VFMADD
     */
    public MixinClientPlayerEntity(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void sendChatMessage(String message, CallbackInfo ci) {

        final String prefix = Tori.INSTANCE.commandManager.PREFIX;

        if (message.startsWith(prefix)) {
            final String commandInput = message.replaceFirst(prefix, "");
            // if it did not successfully go to a command, go to the module commands
            if (!Tori.INSTANCE.commandManager.dispatchRunnable(commandInput)) {
                if (!Tori.INSTANCE.moduleManager.dispatchRunnable(commandInput)) {
                    Logger.log("nothin found D:");
                }
            }
            ci.cancel();
        }
    }


    /**
     * Cancel, otherwise alterations to the event will not take place
     * this is to ensure that movements are not made accidentally, as that could be problematic
     */
    @Inject(method = "move", at = @At("HEAD"), cancellable = true)
    private void move(MovementType type, Vec3d movement, CallbackInfo ci) {
        final MoveEvent event = new MoveEvent(movement.x, movement.y, movement.z);
        Tori.INSTANCE.eventManager.dispatch(event);
        if (event.isCanceled()) {
            ci.cancel();
            double d = this.getX();
            double e = this.getZ();
            super.move(type, new Vec3d(event.x, event.y, event.z));
            this.autoJump((float) (this.getX() - d), (float) (this.getZ() - e));
        }
    }

    @Inject(method = "tickMovement", at = @At(value = "HEAD"), cancellable = true)
    private void tickMovementEarly(CallbackInfo ci) {
        final InputEvent event = new InputEvent(EventStage.EARLY);
        Tori.INSTANCE.eventManager.dispatch(event);

        if (event.isCanceled()) {
            ci.cancel();
        }
    }

/*    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/input/Input;tick(Z)V"))
    private void tickWrapper(Input input, boolean bl) {
        final InputEvent event = new InputEvent(EventStage.EARLY);
        event.input = input;
        Tori.INSTANCE.eventManager.dispatch(event);
        if (!event.isCanceled()) event.input.tick(bl);
    }*/

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;getTutorialManager()Lnet/minecraft/client/tutorial/TutorialManager;"))
    private void tickMovementLate(CallbackInfo ci) {
        final InputEvent event = new InputEvent(EventStage.LATE);
        Tori.INSTANCE.eventManager.dispatch(event);
    }


    /**
     * posts an event on send movement packets
     * @param ci callback info
     */
    @Inject(method = "sendMovementPackets", at = @At("HEAD"))
    private void onSendMovementPacketsHead(CallbackInfo ci) {
        this.sendMovementPacketEvent = new SendMovementPacketEvent(this.yaw, this.pitch, this.getY(), this.onGround, EventStage.EARLY);
        Tori.INSTANCE.eventManager.dispatch(this.sendMovementPacketEvent);
    }

    //These all set the updateWalkingPlayer to match with what the event is set to

    @Redirect(method = "sendMovementPackets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getY()D"))
    private double onSendMovementPacketMinY(ClientPlayerEntity clientPlayerEntity) {
        return this.sendMovementPacketEvent.y;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;onGround:Z"))
    private boolean onSendMovementPacketOnGround(ClientPlayerEntity clientPlayerEntity) {
        return this.sendMovementPacketEvent.onGround;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;yaw:F"))
    private float onSendMovementPacketRotationYaw(ClientPlayerEntity clientPlayerEntity) {
        return this.sendMovementPacketEvent.yaw;
    }

    @Redirect(method = "sendMovementPackets", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayerEntity;pitch:F"))
    private float onSendMovementPacketRotationPitch(ClientPlayerEntity clientPlayerEntity) {
        return this.sendMovementPacketEvent.pitch;
    }

    /**
     * post an event at send movement packets at the return
     * @param ci callback info
     */
    @Inject(method = "sendMovementPackets", at = @At("RETURN"))
    private void onSendMovementPacketReturn(CallbackInfo ci) {
        Tori.INSTANCE.eventManager.dispatch(new SendMovementPacketEvent(this.yaw, this.pitch, this.getY(), this.onGround, EventStage.LATE));
    }
}
