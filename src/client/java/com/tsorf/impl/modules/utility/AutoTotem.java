
/**
 * Created: 12/10/2024
 */
package com.tsorf.impl.modules.utility;

import cc.polymorphism.eventbus.RegisterEvent;
import tsorf0.asm.accessors.HandledScreenAccessor;
import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.api.module.setting.implement.ModeSetting;
import com.tsorf.common.QuickImports;
import com.tsorf.common.util.math.TimerUtils;
import com.tsorf.common.util.player.InventoryUtils;
import com.tsorf.impl.events.player.TickEvent;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;

@ModuleInfo(
        name = "AutoTotem",
        description = "Automatically Places Totems In Offhand",
        category = ModuleCategory.UTILITY
)
public class AutoTotem extends BaseModule implements QuickImports {
    private final TimerUtils openTimer = new TimerUtils();
    private final TimerUtils totemTimer = new TimerUtils();
    private final ModeSetting swapMode = new ModeSetting("Swap Mode", "Select the totem swap type", "Hover", "Hover", "Find");

    @RegisterEvent
    private void TickEventListener(TickEvent event) {
        if (event.getMode().equals(TickEvent.Mode.PRE)) {
            if (mc.world == null | mc.player == null) return;
            if (InventoryUtils.findItemInStrictlyInventory(Items.TOTEM_OF_UNDYING) == -1) return;
            if (!(mc.currentScreen instanceof HandledScreen)) {
                openTimer.reset();
                return;
            }
            swap(true);
        }
    }

    public void swap(boolean offhand) {
        if (mc.world == null | mc.player == null) return;
        if (!openTimer.hasReached(100)) return;
        if (!totemTimer.hasReached(100)) return;
        var slot = InventoryUtils.findItemInStrictlyInventory(Items.TOTEM_OF_UNDYING);
        int mainSlot = 0;
        if (mc.currentScreen instanceof InventoryScreen inv) {
            switch (swapMode.getValue()) {
                case "Hover":
                    Slot focusedSlot = ((HandledScreenAccessor) inv).getFocusedSlot();
                    if (focusedSlot != null && focusedSlot.hasStack() && focusedSlot.getStack().getItem() == Items.TOTEM_OF_UNDYING && focusedSlot.id != 40) {
                        if (offhand) {
                            InventoryUtils.swapItemToOffhand(focusedSlot.id);
                            totemTimer.reset();
                        } else {
                            if (mc.player.getInventory().getStack(mainSlot).getItem() != Items.TOTEM_OF_UNDYING && mc.player.getInventory().getStack(mainSlot).isEmpty()) {
                                mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, focusedSlot.id, mainSlot, SlotActionType.SWAP, mc.player);
                                totemTimer.reset();
                            }
                        }
                    }
                    break;
                case "Find":
                    if (offhand) {
                        InventoryUtils.swapItemToOffhand(slot);
                        totemTimer.reset();
                    } else {
                        if (mc.player.getInventory().getStack(mainSlot).getItem() != Items.TOTEM_OF_UNDYING && mc.player.getInventory().getStack(mainSlot).isEmpty()) {
                            mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, slot, mainSlot, SlotActionType.SWAP, mc.player);
                            totemTimer.reset();
                        }
                    }
                    break;
            }
        }
    }

    public AutoTotem() {
        getSettingRepository().registerSettings(swapMode);
    }
}
