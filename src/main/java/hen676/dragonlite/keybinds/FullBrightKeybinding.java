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

/**
 * TODO:: Add support for shaders
 */
@Environment(EnvType.CLIENT)
public class FullBrightKeybinding {
    private static KeyBinding keyBindingFullBright;
    public static boolean toggle = false;

    public static void init() {
        keyBindingFullBright = new KeyBinding(
                "key.dragonlite.full_bright",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_3,
                "category.dragonlite.main");

        KeyBindingHelper.registerKeyBinding(keyBindingFullBright);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindingFullBright.wasPressed()) {
                if (client.world == null)
                    return;
                toggle = !toggle;
                if (client.player != null) {
                    Text text = Text.translatable("message.dragonlite.full_bright").append(": ");
                    if (toggle)
                        text = text.copy().append(Text.translatable("message.dragonlite.on").setStyle(Style.EMPTY.withColor(DyeColor.GREEN.getSignColor())));
                    else
                        text = text.copy().append(Text.translatable("message.dragonlite.off").setStyle(Style.EMPTY.withColor(DyeColor.RED.getSignColor())));
                    client.player.sendMessage(text,true);
                }
            }
        });
    }
}
