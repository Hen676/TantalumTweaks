package hen676.tantalumtweaks.keybinds;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TweakKeybinding {
    static Text ON = Text.translatable("message.tantalumtweaks.on").styled(style -> style.withColor(Formatting.GREEN));
    static Text OFF = Text.translatable("message.tantalumtweaks.off").styled(style -> style.withColor(Formatting.RED));
    static Formatting TEXT_COLOR = Formatting.GRAY;

    public static KeyBinding register(String translationKey, int key) {
        KeyBinding keyBinding = new KeyBinding(
                translationKey,
                InputUtil.Type.KEYSYM,
                key,
                "category.tantalumtweaks.main");

        KeyBindingHelper.registerKeyBinding(keyBinding);
        return keyBinding;
    }
}
