package hen676.tantalumtweaks.keybinds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class AutoUseKeybinding extends TweakKeybinding {
    private static KeyBinding keyBinding;
    private static boolean toggle = false;

    public static void init() {
        keyBinding = register("key.tantalumtweaks.auto_use", GLFW.GLFW_KEY_KP_5);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            while (keyBinding.wasPressed()) {
                toggle = !toggle;
                client.options.useKey.setPressed(toggle);
                MutableText text = Text.translatable("message.tantalumtweaks.auto_use")
                        .styled(style -> style.withColor(Formatting.GRAY))
                        .append(" ");
                if (toggle)
                    text.append(ON);
                else
                    text.append(OFF);
                client.player.sendMessage(text, true);
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            if (!client.options.useKey.isPressed() && toggle) {
                toggle = false;
                MutableText text = Text.translatable("message.tantalumtweaks.auto_use")
                        .styled(style -> style.withColor(Formatting.GRAY))
                        .append(" ")
                        .append(OFF);
                client.player.sendMessage(text, true);
            }
        });
    }


}
