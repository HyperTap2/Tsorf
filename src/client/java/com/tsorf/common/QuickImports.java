package com.tsorf.common;

import tsorf0.Tsorf;
import com.tsorf.api.module.ModuleManager;
import com.tsorf.api.repository.bot.BotRepository;
import com.tsorf.api.repository.friend.FriendRepository;
import com.tsorf.api.repository.team.TeamRepository;
import com.tsorf.api.system.notification.NotificationManager;
import com.tsorf.api.system.rotation.RotationComponent;
import net.minecraft.client.MinecraftClient;

public interface QuickImports {
    MinecraftClient mc = MinecraftClient.getInstance();
    TeamRepository teamRepository = Tsorf.getInstance().getTeamRepository();
    FriendRepository friendRepository = Tsorf.getInstance().getFriendRepository();
    BotRepository npcRepository = Tsorf.getInstance().getNpcRepository();
    RotationComponent rotationComponent = Tsorf.getInstance().getRotationComponent();
    NotificationManager notificationManager = Tsorf.getInstance().getNotificationManager();
    ModuleManager moduleManager = Tsorf.getInstance().getModuleManager();
}
