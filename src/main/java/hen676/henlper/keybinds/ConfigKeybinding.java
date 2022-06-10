package hen676.henlper.keybinds;

import hen676.henlper.gui.screen.ConfigScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ConfigKeybinding {
    private static KeyBinding keyBindingConfig;

    public static void init() {
        keyBindingConfig = new KeyBinding(
                "key.henlper.config",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                "category.henlper.main");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindingConfig.wasPressed()) {
                if (client.world == null)
                    return;
                client.setScreen(new ConfigScreen(client.currentScreen));
            }
        });
    }
}
