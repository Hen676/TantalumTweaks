package hen676.dragonlite.keybinds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class AutoAttackKeybinding extends DragonLiteKeybinding {
    private static KeyBinding keyBinding;
    private static boolean toggle = false;

    public static void init() {
        keyBinding = register("key.dragonlite.auto_attack", GLFW.GLFW_KEY_KP_4);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            while (keyBinding.wasPressed()) {
                toggle = !toggle;
                client.options.attackKey.setPressed(toggle);
                MutableText text = Text.translatable("message.dragonlite.auto_attack")
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
            if (!client.options.attackKey.isPressed() && toggle) {
                toggle = false;
                MutableText text = Text.translatable("message.dragonlite.auto_use")
                        .styled(style -> style.withColor(TEXT_COLOR))
                        .append(" ")
                        .append(OFF);
                client.player.sendMessage(text, true);
            }

        });
    }
}
