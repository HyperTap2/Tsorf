/**
 * Created: 2/4/2025
 */

package tsorf0.asm.mixins;

import tsorf0.Tsorf;
import com.tsorf.common.QuickImports;
import com.tsorf.impl.modules.other.Streamer;
import net.minecraft.text.TextVisitFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(TextVisitFactory.class)
public class TextVisitFactoryMixin implements QuickImports {
    @ModifyArg(method =
            {"visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z"},
            at=@At(value = "INVOKE", target = "Lnet/minecraft/text/TextVisitFactory;visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z", ordinal = 0), index = 0)
    private static String injectVisitFormatted(String string) {
        if (Tsorf.getInstance() == null || Tsorf.getInstance().getModuleManager() == null) return string;
        if (!Tsorf.getInstance().getModuleManager().getModule(Streamer.class).isEnabled()) return string;
        if (mc.player == null) return string;
        return string.replace(mc.player.getNameForScoreboard(), "Tsorf User");
    }
}