/**
 * Created: 2/16/2025
 */

package com.tsorf.core.listener.impl;

import cc.polymorphism.eventbus.RegisterEvent;
import com.tsorf.api.module.Module;
import com.tsorf.common.QuickImports;
import com.tsorf.common.util.player.ChatUtils;
import com.tsorf.common.util.player.input.InputUtils;
import com.tsorf.core.listener.Listener;
import com.tsorf.impl.events.input.KeyPressEvent;
import lombok.Setter;
import org.lwjgl.glfw.GLFW;

public class KeyPressEventListener implements Listener, QuickImports {

    @Setter
    private static Module moduleToBindTo;

    @RegisterEvent
    private void keyPressEventListener(KeyPressEvent event) {
        if (moduleToBindTo != null && event.getAction() == GLFW.GLFW_PRESS) {
            moduleToBindTo.setKey(event.getKey());
            ChatUtils.sendFormattedMessage("Bound " + moduleToBindTo.getName() + " to key: " + InputUtils.getKeyName(event.getKey()));
            moduleToBindTo = null;
        }
    }
}
