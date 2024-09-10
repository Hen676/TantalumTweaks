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

@Environment(EnvType.CLIENT)
public class LightLevelKeybinding {
    private static KeyBinding keyBindingLightLevel;
    private static boolean toggle = false;

    public static void init() {
        keyBindingLightLevel = new KeyBinding(
                "key.dragonlite.light_level",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_1,
                "category.dragonlite.main");

        KeyBindingHelper.registerKeyBinding(keyBindingLightLevel);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindingLightLevel.wasPressed()) {
                if (client.world == null)
                    return;
                toggle = !toggle;
                if (client.player != null) {
                    MutableText text = Text.translatable("message.dragonlite.light_level")
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

    public static boolean isLightLevel() {
        return toggle;
    }
}
