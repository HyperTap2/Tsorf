package com.tsorf.impl.modules.other;

import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;

@ModuleInfo(
        name        = "MultiTask",
        description = "Lets you use items and attack at the same time",
        category    = ModuleCategory.OTHER
)
public class MultiTask extends BaseModule {}     /* handled in MinecraftClientMixin */