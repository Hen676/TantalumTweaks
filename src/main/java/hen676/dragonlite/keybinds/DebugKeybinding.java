package hen676.dragonlite.keybinds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class DebugKeybinding {
    private static KeyBinding keyBindingDebug;
    private static boolean toggle = false;

    public static void init() {
        keyBindingDebug = new KeyBinding(
                "key.dragonlite.debug",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                "category.dragonlite.main");

        KeyBindingHelper.registerKeyBinding(keyBindingDebug);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindingDebug.wasPressed()) {
                if (client.world == null)
                    return;
                toggle = !toggle;
            }
        });
    }

    public static boolean isDebug() {
        return toggle;
    }
}
