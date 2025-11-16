package tsorf0.asm.mixins;

import tsorf0.Tsorf;
import com.tsorf.common.QuickImports;
import com.tsorf.core.listener.impl.TickEventListener;
import com.tsorf.impl.events.player.SlowdownEvent;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements QuickImports {

    @ModifyExpressionValue(method = {"sendMovementPackets", "tick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getYaw()F"))
    private float sendMovementPacketsTickInjectYaw(float original) {
        TickEventListener rotationManager = (TickEventListener) Tsorf.getInstance().getListenerRepository().getListeners().get(0);
        float[] rotation = rotationManager.getCurrentRotation();
        if (rotation == null || !rotationManager.isRotating()) {
            return original;
        }

        return rotation[0];
    }

    @ModifyExpressionValue(method = {"sendMovementPackets", "tick"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getPitch()F"))
    private float sendMovementPacketsTickInjectPitch(float original) {
        TickEventListener rotationManager = (TickEventListener) Tsorf.getInstance().getListenerRepository().getListeners().get(0);
        float[] rotation = rotationManager.getCurrentRotation();
        if (rotation == null || !rotationManager.isRotating()) {
            return original;
        }

        return rotation[1];
    }

    @Redirect(method = "tickMovement",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z",
                    ordinal = 0
            )
    )
    public boolean onSlowdown(ClientPlayerEntity instance) {
        SlowdownEvent event = new SlowdownEvent(instance.isUsingItem());
        Tsorf.getInstance().getEventManager().post(event);
        return event.isSlowdown();
    }
}
