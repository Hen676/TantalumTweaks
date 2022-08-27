package hen676.dragonlite;

import hen676.dragonlite.config.ConfigLoader;
import hen676.dragonlite.keybinds.HealthBarKeybinding;
import hen676.dragonlite.keybinds.LightLevelKeybinding;
import hen676.dragonlite.keybinds.ZoomKeybinding;
import hen676.dragonlite.option.Options;
import hen676.dragonlite.render.LightLevelRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonLite implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Dragon Lite");
    public static final String MOD_ID = "dragonlite";

    @Override
    public void onInitializeClient() {
        ConfigLoader.init();
        Options.Load();
        WorldRenderEvents.AFTER_TRANSLUCENT.register(LightLevelRenderer::render);

        ZoomKeybinding.init();
        LightLevelKeybinding.init();
        HealthBarKeybinding.init();

        // Built-in resource pack
        FabricLoader.getInstance().getModContainer(MOD_ID)
                .map(modContainer -> ResourceManagerHelper.registerBuiltinResourcePack(
                        new Identifier(MOD_ID, MOD_ID),
                        modContainer,
                        ResourcePackActivationType.NORMAL))
                .filter(success -> !success)
                .ifPresent(success -> LOGGER.warn("Could not register built-in resource pack."));
    }
}
