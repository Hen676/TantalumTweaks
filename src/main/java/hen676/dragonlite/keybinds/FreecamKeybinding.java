package hen676.dragonlite.keybinds;

import hen676.dragonlite.config.Config;
import hen676.dragonlite.entity.FreecamEntity;
import hen676.dragonlite.util.PositionUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;

/**
 * TODO:: Close screens when entering Freecam. Maybe ignore [esc] screen?
 * See line 70 for above
 * TODO:: Remove freecam entity model to not create shadow with shaders
 */
@Environment(EnvType.CLIENT)
public class FreecamKeybinding {
    private static KeyBinding keyBindingFreecam;
    private static boolean toggle = false;
    private static FreecamEntity freecamEntity;
    private static Perspective orignalPerspective;

    public static void init() {
        keyBindingFreecam = new KeyBinding(
                "key.dragonlite.freecam",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F4,
                "category.dragonlite.main");

        KeyBindingHelper.registerKeyBinding(keyBindingFreecam);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindingFreecam.wasPressed())
                toggleFreecam(client);
        });
    }

    public static void toggleFreecam(MinecraftClient client) {
        toggle = !toggle;
        if(toggle)
            onEnableFreeCamera(client);
        else
            onDisableFreeCamera(client);
    }

    public static boolean isFreecam() {
        return toggle;
    }

    public static FreecamEntity getFreeCameraEntity() {
        return freecamEntity;
    }

    public static void onEnableFreeCamera(MinecraftClient client) {
        client.chunkCullingEnabled = false;
        client.gameRenderer.setRenderHand(false);
        //client.currentScreen.close(); check if this is fine
        orignalPerspective = client.options.getPerspective();
        // Create freecam entity
        freecamEntity = new FreecamEntity(-420, new PositionUtil(client.gameRenderer.getCamera()));
        freecamEntity.create();
        // Then set perspective
        client.options.setPerspective(Perspective.FIRST_PERSON);
        // Then create the entity so camera does inherit the wrong perspective
        client.setCameraEntity(freecamEntity);

        if (client.player != null)
            client.player.sendMessage(Text
                    .translatable("message.dragonlite.freecam")
                    .styled(style -> style.withColor(Formatting.DARK_GRAY))
                    .append(" ")
                    .append(Text
                            .translatable("message.dragonlite.on")
                            .styled(style -> style.withColor(Formatting.GREEN))),true);
    }

    public static void onDisableFreeCamera(MinecraftClient client) {
        client.chunkCullingEnabled = true;
        client.gameRenderer.setRenderHand(true);
        client.options.setPerspective(orignalPerspective);
        client.setCameraEntity(client.player);
        freecamEntity.delete();
        freecamEntity.input = new Input();
        freecamEntity = null;

        if (client.player != null) {
            client.player.input = new KeyboardInput(client.options);
        }

        if (client.player != null)
            client.player.sendMessage(Text
                    .translatable("message.dragonlite.freecam")
                    .styled(style -> style.withColor(Formatting.DARK_GRAY))
                    .append(" ")
                    .append(Text
                            .translatable("message.dragonlite.off")
                            .styled(style -> style.withColor(Formatting.RED))), true);
    }
}
