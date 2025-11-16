
/**
 * Created: 12/7/2024
 */
package com.tsorf.api.module;

import com.tsorf.api.module.setting.SettingRepository;

public interface Module {
    void onDisable();

    void onEnable();

    ModuleCategory getCategory();

    String getDescription();

    boolean isEnabled();

    int getKey();

    void setKey(int keyCode);

    String getName();

    String getSuffix();

    SettingRepository getSettingRepository();

    float getWidth();

    float getHeight();

    boolean isDraggable();
}

