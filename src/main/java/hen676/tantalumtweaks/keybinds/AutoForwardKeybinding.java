package hen676.tantalumtweaks.keybinds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class AutoForwardKeybinding extends TweakKeybinding {
    private static KeyBinding keyBinding;
    private static boolean toggle = false;

    public static void init() {
        keyBinding = register("key.tantalumtweaks.auto_forward", GLFW.GLFW_KEY_KP_6);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            while (keyBinding.wasPressed()) {
                toggle = !toggle;
                client.options.forwardKey.setPressed(toggle);
                MutableText text = Text.translatable("message.tantalumtweaks.auto_forward")
                        .styled(style -> style.withColor(TEXT_COLOR))
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
            if (!client.options.forwardKey.isPressed() && toggle) {
                toggle = false;
                MutableText text = Text.translatable("message.tantalumtweaks.auto_forward")
                        .styled(style -> style.withColor(TEXT_COLOR))
                        .append(" ")
                        .append(OFF);
                client.player.sendMessage(text, true);
            }
        });
    }


}
