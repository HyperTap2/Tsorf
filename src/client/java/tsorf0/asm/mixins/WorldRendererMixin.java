/**
 * Created: 2/15/2025
 */

package tsorf0.asm.mixins;

import tsorf0.Tsorf;
import com.tsorf.common.QuickImports;
import com.tsorf.impl.modules.visual.PlayerESP;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin implements QuickImports  {
    @Unique
    private boolean shouldRenderOutline(Entity entity) {
        PlayerESP module = Tsorf.getInstance().getModuleManager().getModule(PlayerESP.class);
        entity.setGlowing(true);
        return true;
    }
}