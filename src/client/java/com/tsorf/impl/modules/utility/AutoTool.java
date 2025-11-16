
/**
 * Created: 12/10/2024
 */
package com.tsorf.impl.modules.utility;

import cc.polymorphism.eventbus.RegisterEvent;
import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.api.module.setting.implement.BooleanSetting;
import com.tsorf.api.module.setting.implement.MultiSetting;
import com.tsorf.api.module.setting.implement.NumberSetting;
import com.tsorf.api.module.setting.implement.SettingCategory;
import com.tsorf.common.QuickImports;
import com.tsorf.common.util.math.TimerUtils;
import com.tsorf.common.util.player.InventoryUtils;
import com.tsorf.common.util.player.input.InputUtils;
import com.tsorf.common.util.world.BlockUtils;
import com.tsorf.impl.events.player.TickEvent;
import net.minecraft.block.BlockState;
import net.minecraft.item.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

@ModuleInfo(
    name        = "AutoTool",
    description = "Switches To Best Mining Tool",
    category    = ModuleCategory.UTILITY
)
public class AutoTool extends BaseModule implements QuickImports {
    private final SettingCategory behaviorSettings = new SettingCategory("Behavior");
    private final SettingCategory conditionsSettings = new SettingCategory("Conditions");

    private final BooleanSetting switchBack = new BooleanSetting(behaviorSettings, "Switch Back", "", true);
    private final NumberSetting<Integer> activationTime = new NumberSetting<>(behaviorSettings, "Activation Time", "", 50, 0, 1000);
    private final NumberSetting<Integer> switchBackTime = new NumberSetting<>(behaviorSettings, "Switch Back Time", "", 250, 0, 1000);

    private final MultiSetting conditions = new MultiSetting(conditionsSettings, "Conditionals", "Set Conditions To Switch", "Only While Sneaking", "Not In Lava", "Not In Water");



    private int originalSlot = -1;
    private boolean isMining = false;
    private TimerUtils switchBackTimer = new TimerUtils();
    private TimerUtils miningTimer = new TimerUtils();

    @RegisterEvent
    private void tickEventEventListener(TickEvent event) {
        if (mc.player == null || mc.world == null || mc.interactionManager == null || mc.currentScreen != null) return;
        if (InputUtils.mouseDown(0)) {
            if (miningTimer.hasReached(activationTime.getValue()) && mc.interactionManager.isBreakingBlock()) {
                if (originalSlot == -1) originalSlot = mc.player.getInventory().selectedSlot;
                isMining = true;
            }
            switchBackTimer.reset();
        } else {
            miningTimer.reset();
            if (isMining && switchBackTimer.hasReached(switchBackTime.getValue())) {
                isMining = false;
                if (switchBack.getValue() && originalSlot != -1 && switchBackTimer.hasReached(switchBackTime.getValue())) {
                    InventoryUtils.swapToMainHand(originalSlot);
                    originalSlot = -1;
                }
            }
        }

        if (conditions.getSpecificValue("Only While Sneaking") && !mc.player.isSneaking()) return;
        if (conditions.getSpecificValue("Not In Water") && BlockUtils.isLiquid(mc.player.getBlockPos())) return;
        if (conditions.getSpecificValue("Not In Lava") && mc.player.isInLava()) return;

        BlockHitResult hitResult = mc.crosshairTarget instanceof BlockHitResult ? (BlockHitResult) mc.crosshairTarget : null;
        if (mc.crosshairTarget.getType() != HitResult.Type.BLOCK) return;
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = mc.world.getBlockState(pos);
        if (!BlockUtils.isBreakable(pos)) return;
        if (isMining) {
            int bestSlot = -1;
            float bestSpeed = 0;
            for (int i = 0; i < 9; i++) {
                ItemStack stack = mc.player.getInventory().getStack(i);

                if (stack.isEmpty()) continue;

                float speed = stack.getMiningSpeedMultiplier(state);
                if (!(stack.getItem() instanceof ShearsItem
                        || stack.getItem() instanceof AxeItem
                        || stack.getItem() instanceof ShovelItem
                        || stack.getItem() instanceof PickaxeItem
                        || stack.getItem() instanceof HoeItem)) continue;

                if (speed > bestSpeed) {
                    bestSpeed = speed;
                    bestSlot = i;
                }
            }
            if (bestSlot != -1 && bestSlot != mc.player.getInventory().selectedSlot) {
                InventoryUtils.swapToMainHand(bestSlot);
            }
        }

    }
    public AutoTool() {
        getSettingRepository().registerSettings(behaviorSettings, conditionsSettings, switchBack, activationTime, switchBackTime, conditions);
    }
}
