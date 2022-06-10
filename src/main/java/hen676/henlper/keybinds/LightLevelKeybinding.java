package hen676.henlper.keybinds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class LightLevelKeybinding {
    private static KeyBinding keyBindingLightLevel;
    public static boolean toggle = false;

    public static void init() {
        keyBindingLightLevel = new KeyBinding(
                "key.henlper.light_level",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                "category.henlper.main");

        KeyBindingHelper.registerKeyBinding(keyBindingLightLevel);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindingLightLevel.wasPressed()) {
                if (client.world == null)
                    return;
                toggle = !toggle;
            }
        });
    }
}
