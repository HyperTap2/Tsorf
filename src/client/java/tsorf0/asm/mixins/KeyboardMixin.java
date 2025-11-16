package tsorf0.asm.mixins;

import tsorf0.Tsorf;
import com.tsorf.api.module.ModuleSwitcher;
import com.tsorf.impl.events.input.KeyPressEvent;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
    @Inject(method = "onKey", at = @At("HEAD"))
    public void onKey(long window, int key, int scancode, int action, int mods, CallbackInfo info) {
        if (scancode < 0 || key == GLFW.GLFW_KEY_UNKNOWN) {
            return;
        }
        if (Tsorf.getInstance() == null || Tsorf.getInstance().getModuleManager() == null) {
            return;
        }
        if (MinecraftClient.getInstance().currentScreen instanceof ChatScreen) return;
        if (action == GLFW.GLFW_PRESS) {
            Tsorf.getInstance().getModuleManager().getModules().forEach(module -> {
                if (module.getKey() == key) {
                    new ModuleSwitcher(Tsorf.getInstance().getModuleManager()).toggleModule(module.getClass());
                }
            });
        }

        KeyPressEvent event = new KeyPressEvent(key, scancode, action, mods);
        if (MinecraftClient.getInstance().currentScreen instanceof ChatScreen) return;
        if (Tsorf.getInstance().getEventManager() != null) {
            Tsorf.getInstance().getEventManager().post(event);
        }
    }
}