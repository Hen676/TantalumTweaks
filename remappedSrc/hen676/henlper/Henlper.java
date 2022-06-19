package hen676.henlper;

import hen676.henlper.config.ConfigLoader;
import hen676.henlper.keybinds.ConfigKeybinding;
import hen676.henlper.keybinds.HealthBarKeybinding;
import hen676.henlper.keybinds.LightLevelKeybinding;
import hen676.henlper.keybinds.ZoomKeybinding;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Henlper implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("Henlper");
    public static final String MOD_ID = "henlper";

    @Override
    public void onInitializeClient() {
        ConfigLoader.init();

        ZoomKeybinding.init();
        ConfigKeybinding.init();
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
