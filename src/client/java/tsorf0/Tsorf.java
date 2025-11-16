package tsorf0;

import cc.polymorphism.annot.IncludeReference;
import cc.polymorphism.eventbus.EventBus;
import com.tsorf.api.command.CommandManager;
import com.tsorf.api.module.BaseModule;
import com.tsorf.api.module.ModuleManager;
import com.tsorf.api.module.setting.SettingManager;
import com.tsorf.api.repository.bot.BotRepository;
import com.tsorf.api.repository.friend.FriendRepository;
import com.tsorf.api.repository.team.TeamRepository;
import com.tsorf.api.system.notification.NotificationManager;
import com.tsorf.api.system.render.font.Fonts;
import com.tsorf.api.system.rotation.RotationComponent;
import com.tsorf.core.client.ClientInfo;
import com.tsorf.core.client.ConcreteClientInfoProvider;
import com.tsorf.core.client.config.ConfigManager;
import com.tsorf.core.listener.ListenerRepository;
import com.tsorf.impl.modules.client.*;
import com.tsorf.impl.modules.combat.*;
import com.tsorf.impl.modules.movement.*;
import com.tsorf.impl.modules.other.*;
import com.tsorf.impl.modules.utility.*;
import com.tsorf.impl.modules.visual.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.fabricmc.api.ModInitializer;

import java.util.Comparator;

@IncludeReference
@Slf4j
public final class Tsorf implements ModInitializer {
    @Getter
    private static Tsorf instance;
    @Getter
    private ClientInfo clientInfo;
    @Getter
    private EventBus eventManager;
    @Getter
    private ModuleManager moduleManager;
    @Getter
    private SettingManager settingManager;
    @Getter
    private Fonts fonts;
    @Getter
    private FriendRepository friendRepository;
    @Getter
    private BotRepository npcRepository;
    @Getter
    private TeamRepository teamRepository;
    @Getter
    private CommandManager commandManager;
    @Getter
    private ConfigManager configManager;
    @Getter
    private ListenerRepository listenerRepository;
    @Getter
    private RotationComponent rotationComponent;
    @Getter
    private NotificationManager NotificationManager;

    @Override
    public void onInitialize() {
        instance = this;
        log.info("Initializing Tsorf...");
        clientInfo = new ConcreteClientInfoProvider().provideClientInfo();
        log.info("Client info loaded: {} v{}", clientInfo.name(), clientInfo.version());
        fonts = new Fonts();
    }

    public void startGame() {
        if (eventManager == null) {
            eventManager = new EventBus();
            commandManager = new CommandManager();
            moduleManager = new ModuleManager();
            settingManager = new SettingManager();
            configManager = new ConfigManager(clientInfo);
            friendRepository = new FriendRepository();
            npcRepository = new BotRepository();
            teamRepository = new TeamRepository();
            listenerRepository = new ListenerRepository();
            rotationComponent = new RotationComponent();
            NotificationManager = new NotificationManager();
            log.info("Managers initialized successfully");

            java.util.List<Class<? extends BaseModule>> moduleClasses = java.util.Arrays.asList(
                    ClickGUI.class,
                    AimAssist.class,
                    Chams.class,
                    TriggerBot.class,
                    //LagRange.class,
                    AutoTotem.class,
                    AutoCrystal.class,
                    Teams.class,
                    Friends.class,
                    AntiBot.class,
                    Sprint.class,
                    NoSlow.class,
                    AntiDebuff.class,
                    Atmosphere.class,
                    //Blink.class,
                    HitSelection.class,
                    SprintReset.class,
                    AntiResourcePack.class,
                    FastPlace.class,
                    BridgeAssist.class,
                    Arraylist.class,
                    Speed.class,
                    Watermark.class,
                    ElytraSwap.class,
                    AutoStun.class,
                    MultiTask.class,
                    Velocity.class,
                    PlayerESP.class,
                    Backtrack.class,
                    SnapTap.class,
                    HitCrystal.class,
                    AnchorMacro.class,
                    TargetStrafe.class,
                    Nametags.class,
                    ChestStealer.class,
                    FlagDetector.class,
                    MoveFix.class,
                    AimCrosshair.class,
                    StorageESP.class,
                    BedPlates.class,
                    Timer.class,
                    NoDelay.class,
                    AutoTool.class,
                    Target.class,
                    TargetHud.class,
                    Tracers.class,
                    Keybinds.class,
                    GuiMove.class,
                    AutoArmor.class,
                    Hitboxes.class,
                    //TPSSync.class,
                    InventoryManager.class,
                    Scaffold.class,
                    NoPush.class,
                    Streamer.class,
                    NoRotate.class,
                    //ChatBypass.class,
                    Notifications.class,
                    //AutoElytra.class,
                    //AutoWeb.class,
                    MaceSwap.class,
                    FakePlayer.class,
                    Piercing.class,
                    //MaceKiller.class,
                    Insults.class,
                    Scoreboard.class,
                    Radar.class,
                    //Widgets.class,
                    Trajectories.class,
                    WaterSpeed.class,
                    //AutoPlace.class,
                    //AntiWeb.class,
                    //HorseJump.class,
                    NoToast.class,
                    //TransactionConfirmBlinker.class,
                    ElytraPredict.class,
                    TotemAnimation.class,
                    Safety.class,
                    Capes.class,
                    AspectRatio.class,
                    ItemTransforms.class,
                    Criticals.class,
                    //WebSpeed.class,
                    KillAura.class,
                    SelfDestruct.class,
                    TickBase.class,
                    Prevent.class
                    //CrystalAura.class
            );

            moduleClasses.stream()
                    .sorted(Comparator.comparing(Class::getSimpleName))
                    .forEach(moduleClass -> {
                        try {
                            moduleManager.registerModule(moduleClass.getDeclaredConstructor().newInstance());
                        } catch (Exception e) {
                        }
                    });

            listenerRepository.setup();
            commandManager.init();
            log.info("Tsorf initialized successfully!");
        }
    }
}
