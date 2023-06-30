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
public class LightLevelKeybinding {
    private static KeyBinding keyBindingLightLevel;
    public static boolean toggle = false;

    public static void init() {
        keyBindingLightLevel = new KeyBinding(
                "key.dragonlite.light_level",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                "category.dragonlite.main");

        KeyBindingHelper.registerKeyBinding(keyBindingLightLevel);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindingLightLevel.wasPressed()) {
                if (client.world == null)
                    return;
                toggle = !toggle;
                if (client.player != null) {
                    Text text = Text.translatable("message.dragonlite.light_level").append(": ");
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
