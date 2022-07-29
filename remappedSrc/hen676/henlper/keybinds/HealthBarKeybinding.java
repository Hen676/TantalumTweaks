package hen676.dragonlite.keybinds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class HealthBarKeybinding {
    private static KeyBinding keyBindingHealthBar;
    public static boolean toggle = false;

    public static void init() {
        keyBindingHealthBar = new KeyBinding(
                "key.henlper.health_bar",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.henlper.main");

        KeyBindingHelper.registerKeyBinding(keyBindingHealthBar);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindingHealthBar.wasPressed()) {
                if (client.world == null)
                    return;
                toggle = !toggle;
            }
        });
    }
}
