package com.tsorf.api.module;

import tsorf0.Tsorf;
import com.tsorf.api.module.exception.ModuleException;
import com.tsorf.api.module.setting.SettingRepository;
import com.tsorf.api.module.setting.implement.SettingCategory;
import com.tsorf.impl.modules.visual.Notifications;
import lombok.Getter;
import lombok.Setter;


public abstract class BaseModule implements Module {
    @Getter
    private boolean                        enabled           = false;
    @Getter
    private final SettingRepository        settingRepository = new SettingRepository();
    private final ModuleInfo               moduleInfo;
    @Getter
    @Setter
    private int                            keyBind;
    @Getter
    private SettingCategory parent;
    @Getter
    @Setter
    private String suffix;
    @Getter
    @Setter
    private float width, height = 0;
    @Getter
    @Setter
    private boolean isDraggable = false;
    protected BaseModule() {
        moduleInfo = getClass().getAnnotation(ModuleInfo.class);

        if (moduleInfo == null) {
            throw new ModuleException("ModuleInfo annotation is missing on " + getClass().getName());
        }

        this.keyBind = moduleInfo.key();
        this.suffix = moduleInfo.suffix().isEmpty() ? null : moduleInfo.suffix();
    }

    @Override
    public void onDisable() {
        if (enabled) {
            enabled = false;
            Tsorf.getInstance().getEventManager().unregister(this);
            onModuleDisabled();
            if (Tsorf.getInstance().getModuleManager().getModule(Notifications.class).isEnabled() && Tsorf.getInstance().getModuleManager().getModule(Notifications.class).enableNotifications.getValue()) {
                Tsorf.getInstance().getNotificationManager().addNotification("Disabled Module", getName(), 2500);
            }
        }
    }

    @Override
    public void onEnable() {
        if (!enabled) {
            enabled = true;
            Tsorf.getInstance().getEventManager().register(this);
            onModuleEnabled();
            if (Tsorf.getInstance().getModuleManager().getModule(Notifications.class).isEnabled() && Tsorf.getInstance().getModuleManager().getModule(Notifications.class).enableNotifications.getValue()) {
                Tsorf.getInstance().getNotificationManager().addNotification("Enabled Module", getName(), 2500);
            }
        }
    }

    /**
     * Hook for subclasses to perform additional actions when the module is disabled.
     */
    protected void onModuleDisabled() {}

    /**
     * Hook for subclasses to perform additional actions when the module is enabled.
     */
    protected void onModuleEnabled() {}

    @Override
    public ModuleCategory getCategory() {
        return moduleInfo.category();
    }

    @Override
    public String getDescription() {
        return moduleInfo.description();
    }

    @Override
    public int getKey() {
        return keyBind;
    }

    public void setKey(int key) {
        this.keyBind = key;
    }

    @Override
    public String getName() {
        return moduleInfo.name();
    }

    @Override
    public String getSuffix() {
        if (suffix == null) return "";
        return suffix;
    }
    @Override
    public float getWidth() {
        return width;
    }
    @Override
    public float getHeight() {
        return height;
    }
    public boolean isDraggable() {
        return isDraggable;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com