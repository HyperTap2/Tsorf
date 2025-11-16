/**
 * Created: 2/4/2025
 */

package tsorf0.asm.mixins;

import com.tsorf.common.QuickImports;
import com.tsorf.impl.events.misc.WebSlowdownEvent;
import net.minecraft.block.BlockState;
import net.minecraft.block.CobwebBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CobwebBlock.class)
public class CobwebBlockMixin implements QuickImports {

    @Inject(method = "onEntityCollision", at = @At("HEAD"), cancellable = true)
    public void onWebSlowdown(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        WebSlowdownEvent cobwebEvent = new WebSlowdownEvent(WebSlowdownEvent.Mode.PRE);
        cobwebEvent.run();
        if (cobwebEvent.isCancelled()) {
            ci.cancel();
        }
    }
    @Inject(method = "onEntityCollision", at = @At("RETURN"))
    public void onWebSlowdownPost(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        WebSlowdownEvent cobwebEvent = new WebSlowdownEvent(WebSlowdownEvent.Mode.POST);
        cobwebEvent.run();
    }
}