package hen676.dragonlite.keybinds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

/**
 * TODO:: Add support for shaders
 */
@Environment(EnvType.CLIENT)
public class FullBrightKeybinding {
    private static KeyBinding keyBindingFullBright;
    private static boolean toggle = false;

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
                FreecamKeybinding.wasFullBrightOn = toggle;
                if (client.player != null) {
                    MutableText text = Text.translatable("message.dragonlite.full_bright")
                            .styled(style -> style.withColor(Formatting.DARK_GRAY))
                            .append(" ");
                    if (toggle)
                        text.append(Text.translatable("message.dragonlite.on").styled(style -> style.withColor(Formatting.GREEN)));
                    else
                        text.append(Text.translatable("message.dragonlite.off").styled(style -> style.withColor(Formatting.RED)));
                    client.player.sendMessage(text,true);
                }
            }
        });
    }

    public static boolean isToggle() {
        return toggle;
    }

    public static void setToggle(boolean toggle) {
        FullBrightKeybinding.toggle = toggle;
    }
}
