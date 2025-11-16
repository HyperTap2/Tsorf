/**
 * Created: 2/8/2025
 */

package com.tsorf.impl.modules.movement;

import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.common.QuickImports;

@ModuleInfo(
        name = "SnapTap",
        description = "wooting :3",
        category = ModuleCategory.MOVEMENT
)
public class SnapTap extends BaseModule implements QuickImports {

    public static long LEFT_STRAFE_LAST_PRESS_TIME;
    public static long RIGHT_STRAFE_LAST_PRESS_TIME;
    public static long FORWARD_STRAFE_LAST_PRESS_TIME;
    public static long BACKWARD_STRAFE_LAST_PRESS_TIME;

} /* handled in KeybindingMixin */