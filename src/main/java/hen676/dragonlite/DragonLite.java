package hen676.dragonlite;

import hen676.dragonlite.config.ConfigLoader;
import hen676.dragonlite.keybinds.DebugKeybinding;
import hen676.dragonlite.keybinds.HealthBarKeybinding;
import hen676.dragonlite.keybinds.LightLevelKeybinding;
import hen676.dragonlite.keybinds.ZoomKeybinding;
import hen676.dragonlite.gui.screen.option.Options;
import hen676.dragonlite.render.LightLevelRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonLite implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Dragon Lite");
    public static final String MOD_ID = "dragonlite";
    public static final boolean DEBUG = false;

    @Override
    public void onInitializeClient() {
        // Config
        ConfigLoader.init();
        Options.Load();

        // World render event
        WorldRenderEvents.AFTER_TRANSLUCENT.register(LightLevelRenderer::render);

        // Keybindings
        ZoomKeybinding.init();
        LightLevelKeybinding.init();
        HealthBarKeybinding.init();
        DebugKeybinding.init();

        // Built-in resource pack
        FabricLoader.getInstance().getModContainer(MOD_ID)
                .map(modContainer -> ResourceManagerHelper.registerBuiltinResourcePack(
                        new Identifier(MOD_ID, MOD_ID),
                        modContainer,
                        Text.translatable("pack.name.dragonlite"),
                        ResourcePackActivationType.DEFAULT_ENABLED))
                .filter(success -> !success)
                .ifPresent(success -> LOGGER.warn("Could not register built-in resource pack."));
    }
}
