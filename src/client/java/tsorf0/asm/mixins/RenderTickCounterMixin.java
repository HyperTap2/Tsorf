/**
 * Created: 2/10/2025
 */

package tsorf0.asm.mixins;

import tsorf0.Tsorf;
import com.tsorf.impl.modules.other.Timer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderTickCounter.Dynamic.class)
public class RenderTickCounterMixin {

    @Shadow
    private float lastFrameDuration;

    @Inject(method = "beginRenderTick(J)I", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/RenderTickCounter$Dynamic;lastFrameDuration:F", shift = At.Shift.AFTER))
    private void beginRenderTickInject(CallbackInfoReturnable<Integer> callback) {
        if (Tsorf.getInstance() == null || Tsorf.getInstance().getModuleManager() == null) return;
        
        if (!Tsorf.getInstance().getModuleManager().getModule(Timer.class).isEnabled()) return;
        float timerSpeed = (float)(double)
                Tsorf.getInstance().getModuleManager().getModule(Timer.class)
                        .getSettingRepository().getSetting("Speed").getValue();
        if (timerSpeed > 0) {
            lastFrameDuration *= timerSpeed;
        }
    }
}
