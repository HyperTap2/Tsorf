/**
 * Created: 2/9/2025
 */

package com.tsorf.impl.events.player;

import cc.polymorphism.eventbus.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlowdownEvent extends Event {
    private boolean slowdown;

    public SlowdownEvent(boolean slowdown) {
        this.slowdown = slowdown;
    }
}