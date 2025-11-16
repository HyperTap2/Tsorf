package tsorf0.asm.mixins;

import tsorf0.Tsorf;
import com.tsorf.common.QuickImports;
import com.tsorf.impl.events.player.*;
import com.tsorf.impl.events.world.WorldChangeEvent;
import com.tsorf.impl.modules.other.MultiTask;
import com.tsorf.impl.modules.visual.PlayerESP;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.Window;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin implements QuickImports {
    @Shadow
    public int itemUseCooldown;

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setPhase(Ljava/lang/String;)V", ordinal = 1), method = "<init>")
    private void init(Window instance, String phase) {
        instance.setPhase(phase);
        Tsorf.getInstance().startGame();
    }

    @Inject(
        at          = @At("HEAD") ,
        method      = "doItemUse",
        cancellable = true
    )
    private void onDoItemUse(CallbackInfo ci) {
        if (Tsorf.getInstance() == null || Tsorf.getInstance().getEventManager() == null) return;
        
        ItemUseEvent preItemUseEvent = new ItemUseEvent(ItemUseEvent.Mode.PRE);
        Tsorf.getInstance().getEventManager().post(preItemUseEvent);

        if (preItemUseEvent.isCancelled()) {
            ci.cancel();
        }
    }
    @Inject(
            method = "doItemUse",
            at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;itemUseCooldown:I", shift = At.Shift.AFTER)
    )
    private void ItemUseCooldown(CallbackInfo ci) {
        if (Tsorf.getInstance() == null || Tsorf.getInstance().getEventManager() == null) return;
        
        ItemUseCooldownEvent event = new ItemUseCooldownEvent(itemUseCooldown);
        Tsorf.getInstance().getEventManager().post(event);
        itemUseCooldown = event.getSpeed();
    }

    @Inject(
        at          = @At("TAIL") ,
        method      = "doItemUse",
        cancellable = true
    )
    private void onDoItemUsePost(CallbackInfo ci) {
        if (Tsorf.getInstance() == null || Tsorf.getInstance().getEventManager() == null) return;
        
        ItemUseEvent postItemUseEvent = new ItemUseEvent(ItemUseEvent.Mode.POST);
        Tsorf.getInstance().getEventManager().post(postItemUseEvent);

        if (postItemUseEvent.isCancelled()) {
            ci.cancel();
        }
    }
    @Shadow @Final
    public GameOptions options;

    @Shadow @Nullable
    public ClientPlayerInteractionManager interactionManager;

    @Shadow @Nullable public ClientPlayerEntity player;
    @Inject(
            at          = @At("HEAD") ,
            method      = "doAttack",
            cancellable = true
    )
    private void onAttack(CallbackInfoReturnable<Boolean> cir) {
        if (Tsorf.getInstance() == null || Tsorf.getInstance().getEventManager() == null) return;
        
        SwingEvent preSwingEvent = new SwingEvent(SwingEvent.Mode.PRE);
        Tsorf.getInstance().getEventManager().post(preSwingEvent);

        if (preSwingEvent.isCancelled()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(
            at     = @At("TAIL") ,
            method = "doAttack"
    )
    private void onAttackPost(CallbackInfoReturnable<Boolean> cir) {
        if (Tsorf.getInstance() == null || Tsorf.getInstance().getEventManager() == null) return;
        
        SwingEvent postSwingEvent = new SwingEvent(SwingEvent.Mode.POST);
        Tsorf.getInstance().getEventManager().post(postSwingEvent);

        if (postSwingEvent.isCancelled()) {
            cir.cancel();
        }
    }
    @Inject(
        at          = @At("HEAD") ,
        method      = "tick",
        cancellable = true
    )
    private void onTick(CallbackInfo ci) {
        if (Tsorf.getInstance() == null || Tsorf.getInstance().getEventManager() == null) return;
        
        TickEvent preTickEvent = new TickEvent(TickEvent.Mode.PRE);
        Tsorf.getInstance().getEventManager().post(preTickEvent);

        if (preTickEvent.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(
        at          = @At("TAIL") ,
        method      = "tick",
        cancellable = true
    )
    private void onTickPost(CallbackInfo ci) {
        if (Tsorf.getInstance() == null || Tsorf.getInstance().getEventManager() == null) return;
        
        TickEvent postTickEvent = new TickEvent(TickEvent.Mode.POST);
        Tsorf.getInstance().getEventManager().post(postTickEvent);

        if (postTickEvent.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(
            method = "handleBlockBreaking",
            at = @At("HEAD"),
            cancellable = true)
    private void onBlockBreakingPre(boolean breaking, CallbackInfo ci) {
        if (Tsorf.getInstance() == null || Tsorf.getInstance().getEventManager() == null) return;
        
        BlockBreakingEvent blockBreakingEvent = new BlockBreakingEvent(BlockBreakingEvent.Mode.PRE);
        Tsorf.getInstance().getEventManager().post(blockBreakingEvent);

        if (blockBreakingEvent.isCancelled()) {
            ci.cancel();
        }
    }
    @Inject(
            method = "handleBlockBreaking",
            at = @At("TAIL"),
            cancellable = true)
    private void onBlockBreakingPost(boolean breaking, CallbackInfo ci) {
        if (Tsorf.getInstance() == null || Tsorf.getInstance().getEventManager() == null) return;
        
        BlockBreakingEvent blockBreakingEvent = new BlockBreakingEvent(BlockBreakingEvent.Mode.POST);
        Tsorf.getInstance().getEventManager().post(blockBreakingEvent);

        if (blockBreakingEvent.isCancelled()) {
            ci.cancel();
        }
    }
    @Inject(
            method = "hasOutline",
            at = @At("HEAD"),
            cancellable = true)
    private void hasOutline(Entity entity, CallbackInfoReturnable<Boolean> ci) {
        if (Tsorf.getInstance() == null || Tsorf.getInstance().getModuleManager() == null) return;
        
        PlayerESP module = Tsorf.getInstance().getModuleManager().getModule(PlayerESP.class);
        if (module == null || !module.isEnabled()) return;
        if (!module.espMode.getValue().equals("Glow Minecraft")) return;
        if (!entity.isPlayer()) return;
        if (module.exclude.getSpecificValue("Friends") && friendRepository.isFriend(entity.getUuid())) return;
        if (module.exclude.getSpecificValue("Bots") && npcRepository.isNPC(entity.getNameForScoreboard())) return;
        if (module.exclude.getSpecificValue("Teams") && teamRepository.isMemberOfCurrentTeam(entity.getNameForScoreboard())) return;
        ci.setReturnValue(true);
    }
    @ModifyExpressionValue(method = "doItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;isBreakingBlock()Z"))
    private boolean doItemUseModifyIsBreakingBlock(boolean original) {
        return (Tsorf.getInstance().getModuleManager() == null || !Tsorf.getInstance().getModuleManager().isModuleEnabled(MultiTask.class)) && original;
    }
    @ModifyExpressionValue(method = "handleBlockBreaking", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean handleBlockBreakingModifyIsUsingItem(boolean original) {
        return (Tsorf.getInstance().getModuleManager() == null || !Tsorf.getInstance().getModuleManager().isModuleEnabled(MultiTask.class)) && original;
    }
    @ModifyExpressionValue(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z", ordinal = 0))
    private boolean handleInputEventsModifyIsUsingItem(boolean original) {
        return (Tsorf.getInstance().getModuleManager() == null || !Tsorf.getInstance().getModuleManager().isModuleEnabled(MultiTask.class)) && original;
    }

    @Inject(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z", ordinal = 0, shift = At.Shift.BEFORE))
    private void handleInputEventsInjectStopUsingItem(CallbackInfo info) {
        if (Tsorf.getInstance().getModuleManager() != null && Tsorf.getInstance().getModuleManager().isModuleEnabled(MultiTask.class) && mc.player.isUsingItem()) {
            if (!mc.options.useKey.isPressed()) mc.interactionManager.stopUsingItem(mc.player);
            while (mc.options.useKey.wasPressed());
        }
    }

    @Inject(method = "setWorld", at = @At("HEAD"))
    private void setWorldInject(ClientWorld world, CallbackInfo ci) {
        Tsorf.getInstance().getEventManager().post(new WorldChangeEvent(world));
    }
}
