package com.tsorf.impl.modules.combat;

import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleCategory;
import com.tsorf.api.module.ModuleInfo;
import com.tsorf.common.QuickImports;

@ModuleInfo(name = "Piercing", description = "Lets You Pierce Blocks And Entities", category = ModuleCategory.COMBAT)
public class Piercing extends BaseModule implements QuickImports {


    public Piercing() {
        getSettingRepository().registerSettings();
    }

}
