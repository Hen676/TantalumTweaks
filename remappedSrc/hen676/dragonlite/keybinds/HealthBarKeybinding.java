package hen676.dragonlite.keybinds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class HealthBarKeybinding {
    private static KeyBinding keyBindingHealthBar;
    private static boolean toggle = false;

    public static void init() {
        keyBindingHealthBar = new KeyBinding(
                "key.dragonlite.health_bar",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_2,
                "category.dragonlite.main");

        KeyBindingHelper.registerKeyBinding(keyBindingHealthBar);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindingHealthBar.wasPressed()) {
                if (client.world == null)
                    return;
                toggle = !toggle;
                if (client.player != null) {
                    Text text = Text.translatable("message.dragonlite.health_bar").append(": ");
                    if (toggle)
                        text = text.copy().append(Text.translatable("message.dragonlite.on").setStyle(Style.EMPTY.withColor(DyeColor.GREEN.getSignColor())));
                    else
                        text = text.copy().append(Text.translatable("message.dragonlite.off").setStyle(Style.EMPTY.withColor(DyeColor.RED.getSignColor())));
                    client.player.sendMessage(text,true);
                }
            }
        });
    }

    public static boolean isHealthBar() {
        return toggle;
    }
}
