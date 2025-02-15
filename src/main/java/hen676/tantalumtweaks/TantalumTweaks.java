package hen676.tantalumtweaks;

import hen676.tantalumtweaks.command.Commands;
import hen676.tantalumtweaks.config.ConfigLoader;
import hen676.tantalumtweaks.gui.screen.option.Options;
import hen676.tantalumtweaks.gui.tooltip.CompostTooltip;
import hen676.tantalumtweaks.gui.tooltip.DurabilityTooltip;
import hen676.tantalumtweaks.gui.tooltip.FuelTooltip;
import hen676.tantalumtweaks.gui.tooltip.FoodTooltip;
import hen676.tantalumtweaks.gui.tooltip.EffectTooltip;
import hen676.tantalumtweaks.keybinds.*;
import hen676.tantalumtweaks.render.LightLevelRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TantalumTweaks implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Tantalum Tweaks");
    public static final String MOD_ID = "tantalumtweaks";
    public static final MinecraftClient MC = MinecraftClient.getInstance();
    public static final boolean DEBUG = FabricLoader.getInstance().isDevelopmentEnvironment();

    @Override
    public void onInitializeClient() {
        // Config and options screen
        ConfigLoader.init();
        Options.Load();

        // World render event
        WorldRenderEvents.BEFORE_DEBUG_RENDER.register(LightLevelRenderer::render);

        // Tooltip events TODO:: make one event call
        ItemTooltipCallback.EVENT.register(DurabilityTooltip::onItemTooltip);
        ItemTooltipCallback.EVENT.register(FoodTooltip::onItemTooltip);
        ItemTooltipCallback.EVENT.register(FuelTooltip::onItemTooltip);
        ItemTooltipCallback.EVENT.register(CompostTooltip::onItemTooltip);
        ItemTooltipCallback.EVENT.register(EffectTooltip::onItemTooltip);

        // Commands
        ClientCommandRegistrationCallback.EVENT.register(Commands::init);

        // Keybindings
        AutoAttackKeybinding.init();
        AutoUseKeybinding.init();
        AutoForwardKeybinding.init();
        ZoomKeybinding.init();
        LightLevelKeybinding.init();
        HealthBarKeybinding.init();
        FreecamKeybinding.init();
        if (DEBUG)
            DebugKeybinding.init();

        // Built-in resource pack
        FabricLoader.getInstance().getModContainer(MOD_ID)
                .map(modContainer -> ResourceManagerHelper.registerBuiltinResourcePack(
                        Identifier.of(MOD_ID, MOD_ID),
                        modContainer,
                        Text.translatable("pack.name.tantalumtweaks"),
                        ResourcePackActivationType.DEFAULT_ENABLED))
                .filter(success -> !success)
                .ifPresent(success -> LOGGER.warn("Could not register built-in resource pack."));
    }
}
