package hen676.tantalumtweaks.keybinds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class DebugKeybinding extends TweakKeybinding {
    private static KeyBinding keyBinding;
    private static boolean toggle = false;

    public static void init() {
        keyBinding = register("key.tantalumtweaks.debug", GLFW.GLFW_KEY_KP_0);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
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
