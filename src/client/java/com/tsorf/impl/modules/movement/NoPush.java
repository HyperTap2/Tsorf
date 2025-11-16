
/**
 * Created: 12/10/2024
 */
package com.tsorf.impl.modules.movement;

import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.common.QuickImports;

@ModuleInfo(
    name        = "NoPush",
    description = "Prevents Being Pushed",
    category    = ModuleCategory.MOVEMENT
)
public class NoPush extends BaseModule implements QuickImports {
    // handled in entity mixin

}
