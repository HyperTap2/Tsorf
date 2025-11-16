
/**
 * Created: 12/10/2024
 */
package com.tsorf.impl.modules.movement;

import cc.polymorphism.eventbus.RegisterEvent;
import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.common.QuickImports;
import com.tsorf.common.util.math.rotation.RotationUtils;
import com.tsorf.common.util.player.PlayerUtils;
import com.tsorf.impl.events.player.MoveFixEvent;
import net.minecraft.entity.LivingEntity;

@ModuleInfo(
        name = "Target Strafe",
        description = "Strafes Around Your Opponent",
        category = ModuleCategory.MOVEMENT
)
public class TargetStrafe extends BaseModule implements QuickImports {
    @RegisterEvent
    private void moveFixEventEventListener(MoveFixEvent event) {
        if (mc.player == null || mc.world == null) return;
        LivingEntity entity = (LivingEntity) PlayerUtils.getClosestEnemy(6f);
        if (entity == null) return;
        float[] targetRotations = RotationUtils.calculate(entity.getPos());
        event.setYaw(targetRotations[0] + 45);
    }
}