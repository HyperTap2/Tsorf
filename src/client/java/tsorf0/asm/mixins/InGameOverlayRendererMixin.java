package tsorf0.asm.mixins;

import tsorf0.Tsorf;
import com.tsorf.impl.modules.visual.AntiDebuff;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.render.VertexConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {
    @Redirect(method = "renderFireOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumer;color(FFFF)Lnet/minecraft/client/render/VertexConsumer;"))
    private static VertexConsumer antiDebuffFire(VertexConsumer vertexConsumer, float red, float green, float blue, float alpha) {
        if (Tsorf.getInstance().getModuleManager().getModule(AntiDebuff.class).isEnabled() && Tsorf.getInstance().getModuleManager().getModule(AntiDebuff.class).mode.getSpecificValue("Fire")) {
            return vertexConsumer.color(red, green, blue, 0);
        }
        return vertexConsumer.color(red, green, blue, alpha);
    }
}